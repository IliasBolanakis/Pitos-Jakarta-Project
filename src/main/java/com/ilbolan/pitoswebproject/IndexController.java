package com.ilbolan.pitoswebproject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"", "/life-cycle"})
public class IndexController extends HttpServlet {

    /**
     * Typical GET request for index.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // trivial response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/index.jsp")
                .forward(request, response);
    }
}