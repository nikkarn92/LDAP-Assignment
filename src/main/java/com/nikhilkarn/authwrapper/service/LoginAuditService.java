/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to log login attempts, OTP events, and flag potential brute-force or abuse.
 */
@Service
public class LoginAuditService {

    private static final Logger logger = LoggerFactory.getLogger(LoginAuditService.class);

    // In-memory tracking of failed login and OTP attempts (can be moved to Redis for HA)
    private final Map<String, AttemptRecord> loginAttempts = new ConcurrentHashMap<>();
    private final Map<String, AttemptRecord> otpFailures = new ConcurrentHashMap<>();

    private static final int BRUTE_FORCE_THRESHOLD = 5;
    private static final int OTP_FAILURE_THRESHOLD = 3;

    public void logLoginSuccess(String username, String ip) {
        logger.info("[LOGIN_SUCCESS] user={} ip={} time={}", username, ip, Instant.now());
        loginAttempts.remove(username); // clear on success
    }

    public void logLoginFailure(String username, String ip) {
        logger.warn("[LOGIN_FAILURE] user={} ip={} time={}", username, ip, Instant.now());
        trackFailure(loginAttempts, username);

        if (loginAttempts.get(username).count >= BRUTE_FORCE_THRESHOLD) {
            logger.error("[ALERT_BRUTE_FORCE] user={} ip={} failures={}", username, ip, loginAttempts.get(username).count);
        }
    }

    public void logOtpFailure(String username, String ip) {
        logger.warn("[OTP_FAILURE] user={} ip={} time={}", username, ip, Instant.now());
        trackFailure(otpFailures, username);

        if (otpFailures.get(username).count >= OTP_FAILURE_THRESHOLD) {
            logger.error("[ALERT_OTP_ABUSE] user={} ip={} failures={}", username, ip, otpFailures.get(username).count);
        }
    }

    public void logOtpSuccess(String username, String ip) {
        logger.info("[OTP_SUCCESS] user={} ip={} time={}", username, ip, Instant.now());
        otpFailures.remove(username); // clear on success
    }

    /**
     * Utility to track number of failures per username.
     */
    private void trackFailure(Map<String, AttemptRecord> map, String key) {
        map.compute(key, (k, v) -> {
            if (v == null) return new AttemptRecord(1, Instant.now());
            v.count++;
            v.lastFailure = Instant.now();
            return v;
        });
    }

    /**
     * Record of failure attempts per user/IP.
     */
    private static class AttemptRecord {
        int count;
        Instant lastFailure;

        AttemptRecord(int count, Instant time) {
            this.count = count;
            this.lastFailure = time;
        }
    }
}
