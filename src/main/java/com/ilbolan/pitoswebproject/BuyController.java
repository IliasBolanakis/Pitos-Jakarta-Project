package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.utils.Email;
import com.ilbolan.pitoswebproject.forms.OrderForm;
import com.ilbolan.pitoswebproject.models.OrderDAO;
import com.ilbolan.pitoswebproject.models.UserDAO;
import com.ilbolan.pitoswebproject.models.beans.Area;
import com.ilbolan.pitoswebproject.models.AreaDAO;
import com.ilbolan.pitoswebproject.models.beans.Order;
import com.ilbolan.pitoswebproject.models.beans.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@WebServlet("/buy")
public class BuyController extends HttpServlet {

    /**
     * Dispatches to the buy.jsp page and sets the available areas
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Area> areas =  AreaDAO.getAreas();
        request.setAttribute("areas", areas);

        User user = UserDAO.getUserBySession(request.getSession().getId());
        request.getSession().setAttribute("user", user);

        request.getSession().setAttribute("previouspage", "buy");

        if (user!=null) {
            var recentOrders = OrderDAO.getRecentUserOrders(user, 5);
            request.setAttribute("previousOrders", recentOrders);

            String previousOrder = request.getParameter("previousorder");
            if (previousOrder!=null) {
                int prev = Integer.parseInt(previousOrder);
                for (String item: recentOrders.get(prev).getOrderItems().keySet()) {
                    switch (item) {
                        case "Σπανακόπιτα" ->
                                request.setAttribute("spanakopites", recentOrders.get(prev).getOrderItems().get("Σπανακόπιτα"));
                        case "Μανιταρόπιτα" ->
                                request.setAttribute("manitaropites", recentOrders.get(prev).getOrderItems().get("Μανιταρόπιτα"));
                        case "Πρασόπιτα" ->
                                request.setAttribute("prasopites", recentOrders.get(prev).getOrderItems().get("Πρασόπιτα"));
                        case "Μπουρέκι" ->
                                request.setAttribute("bourekia", recentOrders.get(prev).getOrderItems().get("Μπουρέκι"));
                    }
                }
            }
        }

        // trivial response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/buy.jsp")
                .forward(request, response);
    }

    /**
     * Performs form validation for the OrderForm, sets attribute based on result & dispatches back to buy.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // fill form with request parameters
        OrderForm oderForm = new OrderForm(
                request.getParameter("name"),
                request.getParameter("address"),
                Integer.parseInt(request.getParameter("area")),
                request.getParameter("email"),
                request.getParameter("tel"),
                request.getParameter("message"),
                null,
                request.getParameter("offer1") != null,
                request.getParameter("payment"),
                LocalDateTime.now()
        );

        // get all pies from the form
        List<Order> orderItems = List.of(
                new Order(null, 1, null, Integer.parseInt(request.getParameter("OrderSpanakopites"))),
                new Order(null, 2, null, Integer.parseInt(request.getParameter("OrderManitaropites"))),
                new Order(null, 3, null, Integer.parseInt(request.getParameter("OrderPrasopites"))),
                new Order(null, 4, null, Integer.parseInt(request.getParameter("OrderBourekia")))
        );
        oderForm.setOrderItems(orderItems);


        // form validation
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<OrderForm>> errors = validator.validate(oderForm);

            if (errors.isEmpty()) { // no errors

                // send emails
                Email.sendEmailToClientOrder(oderForm);
                Email.sendEmailToAdminOrderForm(oderForm);

                // Store to database
                OrderDAO.storeOrder(oderForm, (User) request.getSession().getAttribute("user"));

                request.setAttribute("areas", AreaDAO.getAreas());
                request.setAttribute("success", true);

            } else { // errors
                StringBuilder errorMessage = new StringBuilder("" +
                        "<p>Η φόρμα περιέχει τα εξής λάθη:</p>" + "<ul>");

                for (var error : errors)
                    errorMessage.append("<li>").append(error.getMessage()).append("</li>");

                errorMessage.append("</ul>");

                request.setAttribute("areas", AreaDAO.getAreas());
                request.setAttribute("errors", errorMessage);
                request.setAttribute("formOrder", oderForm);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // response
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/buy.jsp")
                    .forward(request, response);
        }
    }
}