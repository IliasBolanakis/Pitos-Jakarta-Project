package com.ilbolan.pitoswebproject.security;

import com.ilbolan.pitoswebproject.utils.AppLogger;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;


/**
 * CSRF filter for login page
 */

@WebFilter(filterName = "csrfFilter", urlPatterns = {"/login"})
public class CsrfFilter implements Filter {

    private static final AppLogger logger = AppLogger.getLogger(AppLogger.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        // CSRF token synchronization for login page
        if (session != null && httpRequest.getMethod().equals("POST") && ((HttpServletRequest) request).getRequestURI().equals("/login")) {
            if (!checkToken((HttpServletRequest) request, session)) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
                logger.log(Level.WARNING, "Possible CSRF in login attack from " + request.getLocalAddr());
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean checkToken(HttpServletRequest request, HttpSession session) {
        String csrfToken = (String) session.getAttribute("csrfToken");
        String formToken = request.getParameter("csrfToken");

        if (csrfToken == null || !csrfToken.equals(formToken))
            return false;
        else
            return true;
    }
}