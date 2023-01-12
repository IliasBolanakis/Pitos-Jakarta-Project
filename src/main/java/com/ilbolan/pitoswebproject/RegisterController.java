package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.forms.RegisterForm;
import com.ilbolan.pitoswebproject.models.UserDAO;
import com.ilbolan.pitoswebproject.models.beans.User;
import com.ilbolan.pitoswebproject.utils.Email;
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
import java.util.Set;

@WebServlet(urlPatterns = {"/register","/register-success"})
public class RegisterController extends HttpServlet {

    /**
     * Dispatches to register.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        if (code!=null) {
            boolean success = UserDAO.verifyUser(code);
            request.setAttribute("registerComplete", success);
        }
        // dispatch to register.jsp
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/register.jsp")
                .forward(request, response);
    }

    /**
     * Performs form validation for the RegisterForm, sets attribute based on result & dispatches back to buy.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // fill form with request parameters
        RegisterForm formData = new RegisterForm(
                request.getParameter("fullName"),
                request.getParameter("email"),
                request.getParameter("tel"),
                request.getParameter("address"),
                request.getParameter("username"),
                request.getParameter("password"),
                request.getParameter("passwordConfirm")
        );


        // form validation
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<RegisterForm>> errors = validator.validate(formData);

            if (errors.isEmpty()) { // no errors
                // response
                int id = UserDAO.storeUserUnverified(formData);
                User user = UserDAO.getUserById(id);

                // send email to user
                Email.sendEmailCompleteRegister(user);
                request.setAttribute("success", true);

            } else { // errors
                StringBuilder errorMessage = new StringBuilder("" +
                        "<p>Η φόρμα περιέχει τα εξής λάθη:</p>" +
                        "<ul>");
                for (var error : errors)
                    errorMessage.append("<li>").append(error.getMessage()).append("</li>");

                errorMessage.append("</ul>");

                request.setAttribute("errors", errorMessage);
                request.setAttribute("formData", formData);
            }
        } finally {
            // response
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/register.jsp")
                    .forward(request, response);
        }
    }
}