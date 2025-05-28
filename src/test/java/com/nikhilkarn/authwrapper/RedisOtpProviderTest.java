/**
 * POC Project for LDAP AUTH WRAPPER - RedisOtpProvider Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.provider.RedisOtpProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RedisOtpProviderTest {

    private RedisOtpProvider otpProvider;
    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;

    @BeforeEach
    public void setup() throws Exception {
        redisTemplate = mock(StringRedisTemplate.class);
        valueOps = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        otpProvider = new RedisOtpProvider(redisTemplate);

        // Inject OTP expiry config using reflection
        var field = RedisOtpProvider.class.getDeclaredField("otpExpirySeconds");
        field.setAccessible(true);
        field.set(otpProvider, 300L);
    }

    @Test
    public void testSendOtp_StoresInRedis() {
        otpProvider.send("testuser");
        verify(valueOps, times(1)).set(startsWith("otp:testuser"), anyString(), any());
    }

    @Test
    public void testValidateOtp_Success() {
        when(valueOps.get("otp:testuser")).thenReturn("123456");

        boolean result = otpProvider.validate("testuser", "123456");

        assertTrue(result);
        verify(redisTemplate, times(1)).delete("otp:testuser");
    }

    @Test
    public void testValidateOtp_Failure() {
        when(valueOps.get("otp:testuser")).thenReturn("123456");

        boolean result = otpProvider.validate("testuser", "654321");

        assertFalse(result);
        verify(redisTemplate, never()).delete("otp:testuser");
    }

    @Test
    public void testValidateOtp_Expired() {
        when(valueOps.get("otp:testuser")).thenReturn(null);

        boolean result = otpProvider.validate("testuser", "123456");

        assertFalse(result);
    }
}
