package com.nikhilkarn.authwrapper.model;

/**
 * Response model containing final JWT token after successful MFA verification.
 */
public class JwtTokenResponse {
    private boolean success;
    private String token;

    public JwtTokenResponse() {}

    public JwtTokenResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
