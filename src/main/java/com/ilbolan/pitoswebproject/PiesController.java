package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.models.beans.Pie;
import com.ilbolan.pitoswebproject.models.PieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/pies")
public class PiesController extends HttpServlet {

    /**
     * Initializes all the pies from database and dispatches back to pies.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // prepare bean
        List<Pie> pies = PieDAO.getPies();
        request.setAttribute("pies", pies);

        // dispatch to jsp
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/pies.jsp")
                .forward(request, response);
    }
}