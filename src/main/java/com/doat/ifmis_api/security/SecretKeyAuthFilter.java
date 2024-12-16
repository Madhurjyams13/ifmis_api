package com.doat.ifmis_api.security;

import com.doat.ifmis_api.config.KeyGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class SecretKeyAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String EXPECTED_SECRET_KEY = KeyGenerator.getSecretKeyRead();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        // Check if Authorization header is present and matches the expected secret key
        if (authHeader == null || !authHeader.trim().equals(BEARER_PREFIX + EXPECTED_SECRET_KEY)) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized: Invalid or missing secret key\"}");
            return;
        }

        // If secret key is valid, set authentication context
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "secretKeyUser",
                null,
                Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
