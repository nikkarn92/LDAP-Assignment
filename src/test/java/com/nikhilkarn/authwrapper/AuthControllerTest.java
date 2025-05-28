/**
 * POC Project for LDAP AUTH WRAPPER - Controller Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.model.AuthRequest;
import com.nikhilkarn.authwrapper.model.OtpRequest;
import com.nikhilkarn.authwrapper.model.ApiResponse;
import com.nikhilkarn.authwrapper.service.OtpService;
import com.nikhilkarn.authwrapper.controller.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private com.nikhilkarn.authwrapper.service.LdapAuthService ldapAuthService;

    @Mock
    private OtpService otpService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateSuccess() {
        AuthRequest request = new AuthRequest();
        request.setUsername("jdoe");
        request.setPassword("password123");

        when(ldapAuthService.authenticate("jdoe", "password123")).thenReturn("session-xyz");

        ResponseEntity<ApiResponse> response = authController.authenticate(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("session-xyz", response.getBody().getMessage());
        assertEquals("session-xyz", response.getBody().getMessage());
        verify(otpService, times(1)).sendOtp("jdoe");
    }

    @Test
    public void testVerifyOtpSuccess() {
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setUsername("jdoe");
        otpRequest.setOtp("123456");

        when(otpService.verifyOtp("jdoe", "123456")).thenReturn(true);

        ResponseEntity<ApiResponse> response = authController.verifyOtp(otpRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(null, response.getBody().getMessage());
    }

    @Test
    public void testVerifyOtpFailure() {
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setUsername("jdoe");
        otpRequest.setOtp("000000");

        when(otpService.verifyOtp("jdoe", "000000")).thenReturn(false);

        ResponseEntity<ApiResponse> response = authController.verifyOtp(otpRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals(null, response.getBody().getMessage());
    }
}
