package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.forms.LoginForm;
import com.ilbolan.pitoswebproject.models.UserDAO;
import com.ilbolan.pitoswebproject.security.CSRF;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.Set;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    /**
     * Typical GET method that dispatches to login.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Logging-in as the admin must be protected for CSRF attacks
         * since he has more privileges from the typical user, so we create
         * a unique token on the logging page to ensure that this doesn't happen
         */
        CSRF.generateToken(request);
        // trivial response
        getServletContext().
                getRequestDispatcher("/WEB-INF/templates/login.jsp").
                forward(request, response);
    }

    /**
     * Post method that logs in the user by storing his session in the database
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
         * NOTE: CSRF filtering is performed prior by the CsrfFilter class
         */

        // fill form with request parameters
        LoginForm formData = new LoginForm(
                request.getParameter("username"),
                request.getParameter("password")
        );

        // form validation
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();
            Set<ConstraintViolation<LoginForm>> errors = validator.validate(formData);

            if(errors.isEmpty()) { // no errors
                request.setAttribute("success", true);

                HttpSession session = request.getSession(); // get session from request
                UserDAO.storeSession(formData.getUsername(), session.getId()); // store user session to database

                String previousPage = (String) request.getSession().getAttribute("previouspage");
                if (previousPage == null) {
                    getServletContext()
                            .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                            .forward(request, response);
                } else {
                    response.sendRedirect("/" + previousPage);
                    request.getSession().setAttribute("previouspage", null); // Reset previouspage to null
                }
            }
            else { // errors
                StringBuilder errorMessage = new StringBuilder("" +
                        "<p>Η φόρμα περιέχει τα εξής λάθη:</p>" +
                        "<ul>");
                for (var error : errors)
                    errorMessage.append("<li>").append(error.getMessage()).append("</li>");

                errorMessage.append("</ul>");

                request.setAttribute("errors", errorMessage);
                request.setAttribute("formData", formData);

                // response
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                        .forward(request, response);
            }
        }
    }
}
