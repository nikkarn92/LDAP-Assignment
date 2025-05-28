/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.model;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for OTP verification requests.
 */
public class OtpRequest {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "OTP must not be empty")
    private String otp;

    private String ipAddress;

    public OtpRequest() {}

    public OtpRequest(String username, String otp, String ipAddress) {
        this.username = username;
        this.otp = otp;
        this.ipAddress = ipAddress;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
