package com.ilbolan.pitoswebproject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/store")
public class StoreController extends HttpServlet {

    /**
     * Typical GET method that dispatches to store.jsp
     *
     * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // trivial response
        getServletContext()
                .getRequestDispatcher("/WEB-INF/templates/store.jsp")
                .forward(request, response);
    }
}