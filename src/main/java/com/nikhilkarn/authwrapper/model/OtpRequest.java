package com.nikhilkarn.authwrapper.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OTP verification request containing email and the one-time password")
public class OtpRequest {

    @Schema(description = "Email used to receive the OTP", example = "john.doe@example.com")
    private String email;

    @Schema(description = "One-Time Password sent to email", example = "827364")
    private String otp;

    // Constructors
    public OtpRequest() {}

    public OtpRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
