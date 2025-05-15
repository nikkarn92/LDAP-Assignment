package com.nikhilkarn.authwrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service to handle OTP generation, caching, and verification.
 */
@Service
public class OtpService {

    private final Map<String, String> otpCache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Generate and cache OTP for an email.
     */
    public String generateOtp(String email) {
        String otp = String.valueOf(100000 + (int)(Math.random() * 900000));
        otpCache.put(email, otp);

        // Expire after 5 minutes
        scheduler.schedule(() -> otpCache.remove(email), 5, TimeUnit.MINUTES);
        return otp;
    }

    /**
     * Send OTP via email.
     */
    @Async
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + "\nThis code will expire in 5 minutes.");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
        }
    }

    /**
     * Validate the OTP.
     */
    public boolean verifyOtp(String email, String providedOtp) {
        return providedOtp.equals(otpCache.getOrDefault(email, ""));
    }
}
