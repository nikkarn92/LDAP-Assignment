package com.nikhilkarn.authwrapper.model;

/**
 * Response object after OTP verification succeeds.
 * Contains a signed JWT session token.
 */
public class MfaSessionResponse {

    private boolean success;
    private String sessionToken;

    public MfaSessionResponse() {
    }

    public MfaSessionResponse(boolean success, String sessionToken) {
        this.success = success;
        this.sessionToken = sessionToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
