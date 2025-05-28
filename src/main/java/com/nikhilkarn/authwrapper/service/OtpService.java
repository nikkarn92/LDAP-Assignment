/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.service;

import com.nikhilkarn.authwrapper.provider.OtpProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service for sending and verifying OTP via pluggable provider.
 */
@Service
public class OtpService {

    private final OtpProvider otpProvider;

    @Autowired
    public OtpService(@Qualifier("externalOtpApiProvider") OtpProvider otpProvider) {
        this.otpProvider = otpProvider;
    }

    /**
     * Sends an OTP to the user's registered email or mobile.
     *
     * @param username Username to whom OTP is sent
     */
    public void sendOtp(String username) {
        otpProvider.send(username);
    }

    /**
     * Verifies the OTP for a given user.
     *
     * @param username Username
     * @param otp OTP entered by user
     * @return true if valid, false otherwise
     */
    public boolean verifyOtp(String username, String otp) {
        return otpProvider.validate(username, otp);
    }
}
