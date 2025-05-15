package com.nikhilkarn.authwrapper.util;

import java.security.SecureRandom;

/**
 * Utility class to generate secure One-Time Passwords (OTPs).
 */
public class OtpGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String NUMERIC = "0123456789";
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Generate a numeric OTP (e.g., 6-digit code).
     */
    public static String generateNumericOtp(int length) {
        return generateFromCharset(length, NUMERIC);
    }

    /**
     * Generate an alphanumeric OTP (e.g., for more secure use cases).
     */
    public static String generateAlphanumericOtp(int length) {
        return generateFromCharset(length, ALPHANUMERIC);
    }

    private static String generateFromCharset(int length, String charset) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(charset.charAt(secureRandom.nextInt(charset.length())));
        }
        return otp.toString();
    }
}
