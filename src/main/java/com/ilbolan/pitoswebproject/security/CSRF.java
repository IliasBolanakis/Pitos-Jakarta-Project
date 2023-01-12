package com.ilbolan.pitoswebproject.security;

import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Class that provides methods for prevention of CSRF attacks
 */
public class CSRF {

    private CSRF(){} // no point in creating instances

    /**
     * Generates a random token to be stored in the user's session
     *
     * @param request Contains the user's {@link jakarta.servlet.http.HttpSession} object
     */
    public static void generateToken(HttpServletRequest request) {

        // if we have already created a token for session don't create a new one
        if(request.getSession().getAttribute("csrfToken") != null)
            return;

        // set the session's token
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("csrfToken", token);

    }

    /**
     * Validates the user's csrf token
     *
     * @param request Contains the user's {@link jakarta.servlet.http.HttpSession} object
     *
     * @return True if the token of the form is the same as the session token
     */
    public static boolean validateToken(HttpServletRequest request) {

        // Retrieve the token from the form data and the user's session
        String formToken = request.getParameter("csrfToken");
        String sessionToken = (String) request.getSession().getAttribute("csrfToken");

        return formToken != null && formToken.equals(sessionToken);
    }
}
