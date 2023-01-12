package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.forms.ResetPasswordForm;
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
import java.util.Set;

@WebServlet("/change-password")
public class PasswordResetChangeController extends HttpServlet {

    /**
     * GET method that takes a unique code provided by the request, sets an attribute & dispatches to password-change.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");

        request.setAttribute("code", code);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/password-change.jsp")
                .forward(request, response);
    }

    /**
     * Performs for validation for ResetPasswordForm, sets attributes & dispatches to password-change.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ResetPasswordForm formData = new ResetPasswordForm(
                request.getParameter("password"),
                request.getParameter("passwordConfirm")
        );

        // form validation
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ResetPasswordForm>> errors = validator.validate(formData);

            if (errors.isEmpty()) { // no errors
                String password = request.getParameter("password"); // // get password from request parameters
                String code = request.getParameter("code"); // get code from request parameters
                UserDAO.updateUserNewPassword(password, Integer.parseInt(code));

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
        }finally {
                // response
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/templates/password-change.jsp")
                        .forward(request, response);
        }
    }
}