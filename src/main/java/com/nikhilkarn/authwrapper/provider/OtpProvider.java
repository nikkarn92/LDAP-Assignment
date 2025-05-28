/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.provider;

/**
 * Interface for OTP providers.
 * Allows pluggable implementation for external or internal OTP logic.
 */
public interface OtpProvider {

    /**
     * Sends an OTP to the specified user via email/SMS/other channels.
     *
     * @param username the user to whom the OTP is to be sent
     */
    void send(String username);

    /**
     * Validates the OTP entered by the user.
     *
     * @param username the user verifying the OTP
     * @param otp the one-time password entered
     * @return true if OTP is valid, false otherwise
     */
    boolean validate(String username, String otp);
}
