package com.nikhilkarn.authwrapper.model;

import java.util.List;

/**
 * Shared list of public paths to exclude from security filters like API key and token auth.
 */
public final class PublicPaths {

    public static final List<String> EXCLUDED_PATHS = List.of(
        "/", "/index.html", "/favicon.ico",
        "/clients/register",
        "/swagger-ui", "/swagger-ui/**",
        "/v3/api-docs", "/v3/api-docs/**",
        "/webjars"
    );

    private PublicPaths() {

    }

    public static boolean isExcluded(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    /**
     * Used by SecurityConfig to apply .requestMatchers(...).permitAll()
     */
    public static String[] asArray() {
        return EXCLUDED_PATHS.toArray(new String[0]);
    }
}
