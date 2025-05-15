package com.nikhilkarn.authwrapper.security;

import com.nikhilkarn.authwrapper.model.PublicPaths;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Security filter to validate incoming requests using an API key header.
 * You can register this filter in WebSecurity or directly via FilterRegistrationBean.
 */
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    // Replace this with a secure value from env/secret store
    private static final String EXPECTED_API_KEY = "nikhil-api-key-123";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Intercepted request: " + request.getRequestURI());

        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null || !apiKey.equals(EXPECTED_API_KEY)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized: Missing or invalid API key");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return PublicPaths.isExcluded(request.getRequestURI());
    }

}
