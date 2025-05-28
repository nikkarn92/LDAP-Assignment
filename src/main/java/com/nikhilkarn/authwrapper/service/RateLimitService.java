/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Service to enforce rate limiting and account lockout using Redis.
 */
@Service
public class RateLimitService {

    private final StringRedisTemplate redisTemplate;

    @Value("${security.rate-limiting.login-attempts-threshold:5}")
    private int loginAttemptsThreshold;

    @Value("${security.rate-limiting.lockout-duration-minutes:15}")
    private int lockoutDurationMinutes;

    public RateLimitService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Registers a failed login attempt and locks the user if threshold is exceeded.
     *
     * @param username The username to track
     */
    public void registerFailure(String username) {
        String key = "fail:" + username;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(lockoutDurationMinutes));
        }
    }

    /**
     * Clears failure records after successful login.
     *
     * @param username The username to reset
     */
    public void resetFailures(String username) {
        redisTemplate.delete("fail:" + username);
    }

    /**
     * Checks whether the user is locked out due to excessive failures.
     *
     * @param username Username
     * @return true if locked, false otherwise
     */
    public boolean isLocked(String username) {
        String key = "fail:" + username;
        String val = redisTemplate.opsForValue().get(key);
        if (val == null) return false;
        return Integer.parseInt(val) >= loginAttemptsThreshold;
    }
}
