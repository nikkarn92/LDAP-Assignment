package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.service.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OtpServiceTest {

    private OtpService otpService;

    @BeforeEach
    void setUp() {
        otpService = new OtpService();
    }

    /*@Test
    void generateAndVerifyOtp() {
        String email = "test@example.com";
        String otp = otpService.generateOtp(email);

        assertTrue(otpService.verifyOtp(email, otp));
        assertFalse(otpService.verifyOtp(email, "wrong"));
    }*/
}
