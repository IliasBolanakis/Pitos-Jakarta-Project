package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.utils.Email;
import com.ilbolan.pitoswebproject.forms.ResetEmailForm;
import com.ilbolan.pitoswebproject.models.UserDAO;
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
import java.security.SecureRandom;
import java.util.Set;

@WebServlet("/password-reset")
public class PasswordResetEmailController extends HttpServlet {

    /**
     * Typical GET method that dispatches to password-reset.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // trivial response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/password-reset.jsp")
                .forward(request, response);
    }

    /**
     * Performs form validation for ResetEmailForm sets attributes & dispatches back to password-reset.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String email = request.getParameter("email"); // get email from request parameter

        // fill form
        ResetEmailForm formData = new ResetEmailForm(email);

        // form validation
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ResetEmailForm>> errors = validator.validate(formData);

            if (errors.isEmpty()) { // no errors
                SecureRandom sr = new SecureRandom();
                int code = sr.nextInt(100000);
                Email.sendEmailResetPassword(email, String.valueOf(code));
                UserDAO.updateUserResetPassword(email,code);

                request.setAttribute("success", true);

            } else { // errors
                StringBuilder errorMessage = new StringBuilder("" +
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
                    .getRequestDispatcher("/WEB-INF/templates/password-reset.jsp")
                    .forward(request, response);
        }

    }
}
