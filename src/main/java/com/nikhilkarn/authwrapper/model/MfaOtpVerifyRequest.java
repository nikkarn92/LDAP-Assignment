package com.nikhilkarn.authwrapper.model;

/**
 * Request payload for verifying OTP with otpKey after LDAP authentication.
 */
public class MfaOtpVerifyRequest {
    private String username;
    private String otp;
    private String otpKey;

    public MfaOtpVerifyRequest() {}

    public MfaOtpVerifyRequest(String username, String otp, String otpKey) {
        this.username = username;
        this.otp = otp;
        this.otpKey = otpKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtpKey() {
        return otpKey;
    }

    public void setOtpKey(String otpKey) {
        this.otpKey = otpKey;
    }
}
