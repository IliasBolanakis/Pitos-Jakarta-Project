package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.models.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    /**
     * Typical GET method that dispatches to logout.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // trivial response
        getServletContext().
                getRequestDispatcher("/WEB-INF/templates/logout.jsp").
                forward(request, response);
    }

    /**
     * Checks if user is logged-in, set attributes & dispatches to logout.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(UserDAO.logout(request.getSession().getId())) // no errors
            request.setAttribute("success", true);
        else { // errors
            StringBuilder errorMessage = new StringBuilder("" + "<ul>");
            errorMessage.append("Δεν έχει πραγματοποιηθεί σύνδεση");
            errorMessage.append("</ul>");

            request.setAttribute("errors", errorMessage);
        }
        // response
        getServletContext().
                getRequestDispatcher("/WEB-INF/templates/logout.jsp").
                forward(request, response);
    }
}
