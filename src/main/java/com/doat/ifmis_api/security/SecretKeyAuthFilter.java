package com.doat.ifmis_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class SecretKeyAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(SecretKeyAuthFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    //private static final String EXPECTED_SECRET_KEY = getSecretKeyRead();

    private static final String BASE_LOC_NAME = "user.home";
    private static final String KEY_FILE_PATH = System.getProperty(BASE_LOC_NAME) + "/.secureapi/secret.key";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        String expectedSecretKey = getSecretKeyRead();

        //System.out.println("Received : " + authHeader);
        //System.out.println("Received : " + expectedSecretKey);

        // Check if Authorization header is present and matches the expected secret key
        if (authHeader == null || !authHeader.trim().equals(BEARER_PREFIX + expectedSecretKey)) {
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

        public static String getSecretKeyRead() {

        Path path = Paths.get(KEY_FILE_PATH);
        try {
            return new String(Files.readAllBytes(path));
        }
        catch(IOException e)
        {
            logger.warn("Exception Occurred with {}", e.getMessage());
            return null;
        }

    }
}
