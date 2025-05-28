package com.nikhilkarn.authwrapper.model;

public class AuthResponse {
    private String sessionId;
    private String email;

    public AuthResponse(String sessionId, String email) {
        this.sessionId = sessionId;
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getEmail() {
        return email;
    }
}
