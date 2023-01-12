package com.ilbolan.pitoswebproject;

import com.ilbolan.pitoswebproject.models.beans.Pie;
import com.ilbolan.pitoswebproject.models.PieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pie")
public class PieController extends HttpServlet {

    /**
     * Creates pie bean and dispatches to pie.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // read parameters
        int id = Integer.parseInt(request.getParameter("id"));

        // prepare bean
        Pie pieBean = pieBean = PieDAO.getPieById(id);
        request.setAttribute("bean", pieBean);

        // dispatch to jsp
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/pie.jsp")
                .forward(request, response);
    }
}