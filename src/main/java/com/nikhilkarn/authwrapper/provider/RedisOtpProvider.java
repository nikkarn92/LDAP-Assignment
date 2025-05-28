/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

/**
 * Redis-backed OTP provider (Stateless OTP handling).
 */
@Component("redisOtpProvider")
public class RedisOtpProvider implements OtpProvider {

    private static final Logger logger = LoggerFactory.getLogger(RedisOtpProvider.class);

    private final StringRedisTemplate redisTemplate;
    private final Random random = new SecureRandom();

    @Value("${otp.expiry-seconds:300}")
    private long otpExpirySeconds;

    public RedisOtpProvider(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void send(String username) {
        String otp = generateOtp();
        String key = otpKey(username);

        redisTemplate.opsForValue().set(key, otp, Duration.ofSeconds(otpExpirySeconds));

        // Replace with actual mail/SMS sending logic
        logger.info("[OTP GENERATED] user={} otp={} (valid for {}s)", username, otp, otpExpirySeconds);
    }

    @Override
    public boolean validate(String username, String otp) {
        String key = otpKey(username);
        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp != null && storedOtp.equals(otp)) {
            redisTemplate.delete(key); // one-time use
            return true;
        }
        return false;
    }

    private String otpKey(String username) {
        return "otp:" + username;
    }

    private String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }
}
