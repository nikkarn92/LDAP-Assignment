
package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.controller.AuthController;
import com.nikhilkarn.authwrapper.model.*;
import com.nikhilkarn.authwrapper.service.LdapAuthService;
import com.nikhilkarn.authwrapper.service.OtpService;
import com.nikhilkarn.authwrapper.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private LdapAuthService ldapAuthService;

    @Mock
    private OtpService otpService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateWithMfa() {
        AuthRequest request = new AuthRequest("user", "pass","123456");
        AuthResponse response = new AuthResponse("user@example.com", "sid123");

        when(ldapAuthService.authenticate("user", "pass")).thenReturn(response);
        when(otpService.sendOtpAndReturnKey("user@example.com")).thenReturn("otp-key-123");

        ResponseEntity<?> result = authController.authenticateWithMfa(request);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        MfaOtpKeyResponse body = (MfaOtpKeyResponse) result.getBody();
        assertNotNull(body);
        assertTrue(body.isSuccess());
    }

    @Test
    void testVerifyOtpWithKey_Valid() {
        MfaOtpVerifyRequest request = new MfaOtpVerifyRequest("user", "otp-key-123", "123456");

        when(otpService.verifyOtpWithKey("otp-key-123", "123456")).thenReturn(true);
        when(jwtUtil.generateToken("user")).thenReturn("jwt-token-abc");

        ResponseEntity<?> result = authController.verifyOtpWithKey(request);

        assertFalse(result.getStatusCode().is2xxSuccessful());

    }

    @Test
    void testVerifyOtpWithKey_Invalid() {
        MfaOtpVerifyRequest request = new MfaOtpVerifyRequest("user", "otp-key-123", "wrong-otp");

        when(otpService.verifyOtpWithKey("otp-key-123", "wrong-otp")).thenReturn(false);

        ResponseEntity<?> result = authController.verifyOtpWithKey(request);

        assertEquals(401, result.getStatusCodeValue());
        ApiResponse body = (ApiResponse) result.getBody();
        assertNotNull(body);
        assertFalse(body.isSuccess());
    }
}
