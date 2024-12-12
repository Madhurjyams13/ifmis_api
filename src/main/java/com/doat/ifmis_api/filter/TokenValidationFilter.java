package com.doat.ifmis_api.filter;


import com.doat.ifmis_api.service.TokenService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class TokenValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Skip authentication for specific endpoints if needed
        String path = httpRequest.getRequestURI();
        if (path.startsWith("/public/")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing or invalid authorization header");
            return;
        }

        String token = authHeader.substring(7);
        if (!TokenService.validateToken(token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        chain.doFilter(request, response);
    }
}
