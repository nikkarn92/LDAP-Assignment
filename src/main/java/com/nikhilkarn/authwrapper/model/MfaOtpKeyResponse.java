package com.nikhilkarn.authwrapper.model;

/**
 * Response returned after LDAP authentication and OTP trigger.
 * Contains the otpKey that is used for OTP verification later.
 */
public class MfaOtpKeyResponse {
    private boolean success;
    private String otpKey;
    private String message;

    public MfaOtpKeyResponse() {}

    public MfaOtpKeyResponse(boolean success, String otpKey, String message) {
        this.success = success;
        this.otpKey = otpKey;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
