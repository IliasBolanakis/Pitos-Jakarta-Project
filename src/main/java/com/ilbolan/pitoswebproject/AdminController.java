package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.models.UserDAO;
import com.ilbolan.pitoswebproject.security.CSRF;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    /**
     * Typical GET method that checks if a parameter was given to determine the action to be performed
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String sessionID = request.getSession().getId(); // get session id from request
        String role = UserDAO.getRole(sessionID); // get role of session user

        if(role == null) { // if not logged-in dispatch to login.jsp

            request.setAttribute("errors", "Πρέπει να συνδεθείτε για να αποκτήσετε πρόσβαση");
            CSRF.generateToken(request);
            request.getSession().setAttribute("previouspage", "admin");
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/login.jsp")
                    .forward(request, response);

        } else if(!role.equals("admin")) { // if user is not admin dispatch to unauthorized.jsp

            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/error/unauthorized.jsp")
                    .forward(request, response);

        } else { // else user is admin => allow privileges

            String action = request.getParameter("action");
            if (action!=null) {
                if (request.getParameter("action").equals("1")) {
                    UserDAO.deleteUnverifiedUsers(); // if action = 1 delete all unverified users
                } else if (request.getParameter("action").equals("2")) {
                    UserDAO.updateVerifiedUsersCode(); // else if action = 2 remove user codes for register completion
                }
            }

            List<Integer> adminStats = UserDAO.getAdminStats(); // shows the stats for above users
            request.setAttribute("adminStats", adminStats);

            // trivial response
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/templates/admin.jsp")
                    .forward(request, response);
        }
    }
}