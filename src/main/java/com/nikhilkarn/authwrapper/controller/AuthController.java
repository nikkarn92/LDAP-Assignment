/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.controller;

import com.nikhilkarn.authwrapper.model.*;
import com.nikhilkarn.authwrapper.service.LdapAuthService;
import com.nikhilkarn.authwrapper.service.OtpService;
import com.nikhilkarn.authwrapper.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller exposing endpoints for LDAP authentication and OTP verification.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private LdapAuthService ldapAuthService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Step 1 (Original): Authenticate using LDAP and trigger OTP.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthRequest request) {
        AuthResponse auth = ldapAuthService.authenticate(request.getUsername(), request.getPassword());
        otpService.sendOtpAndReturnKey(auth.getEmail());
        return ResponseEntity.ok(new ApiResponse(true, auth.getSessionId()));
    }

    /**
     * Step 2 (Original): Verify OTP.
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody OtpRequest otpRequest) {
        boolean valid = otpService.verifyOtpWithKey(otpRequest.getUsername(), otpRequest.getOtp());
        if (valid) {
            return ResponseEntity.ok(new ApiResponse(true, null));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse(false, null));
        }
    }

    /**
     * 🔐 Combined Step 1: LDAP auth + OTP trigger + return otpKey
     */
    @PostMapping("/authenticate-mfa")
    public ResponseEntity<?> authenticateWithMfa(@RequestBody AuthRequest request) {
        AuthResponse auth = ldapAuthService.authenticate(request.getUsername(), request.getPassword());
        String otpKey = otpService.sendOtpAndReturnKey(auth.getEmail());
        return ResponseEntity.ok(new MfaOtpKeyResponse(true, otpKey, "OTP sent to registered email."));
    }

    /**
     * 🔐 Combined Step 2: OTP + otpKey → Issue JWT session token
     */
    @PostMapping("/verify-otp-mfa")
    public ResponseEntity<?> verifyOtpWithKey(@RequestBody MfaOtpVerifyRequest request) {
        boolean verified = otpService.verifyOtpWithKey(request.getOtpKey(), request.getOtp());
        if (verified) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new MfaSessionResponse(true, token));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse(false, "Invalid OTP or expired key"));
        }
    }
}
