/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.service;

import com.nikhilkarn.authwrapper.provider.OtpProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for sending and verifying OTP via pluggable provider.
 */
@Service
public class OtpService {

    private final OtpProvider otpProvider;

    // In-memory map to hold otpKey <-> username mapping
    private final ConcurrentHashMap<String, String> otpSessionMap = new ConcurrentHashMap<>();

    @Autowired
    public OtpService(@Qualifier("externalOtpApiProvider") OtpProvider otpProvider) {
        this.otpProvider = otpProvider;
    }

    /**
     * Sends OTP to user and returns a session-specific key.
     * @param username LDAP username
     * @return otpKey - unique token to bind OTP session
     */
    public String sendOtpAndReturnKey(String username) {
        String otpKey = UUID.randomUUID().toString();
        otpProvider.send(username); // Triggers the OTP (email, SMS etc.)
        otpSessionMap.put(otpKey, username); // Save the otpKey mapping
        return otpKey;
    }

    /**
     * Verifies OTP using the otpKey and submitted OTP value.
     * @param otpKey UUID from initial OTP trigger
     * @param otp OTP entered by user
     * @return true if valid, false if invalid or expired
     */
    public boolean verifyOtpWithKey(String otpKey, String otp) {
        String username = otpSessionMap.get(otpKey);
        if (username == null) {
            return false;
        }

        boolean result = otpProvider.validate(username, otp);
        if (result) {
            otpSessionMap.remove(otpKey); // Clean up after success
        }
        return result;
    }
}
