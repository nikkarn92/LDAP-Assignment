/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.controller;

import com.nikhilkarn.authwrapper.model.AuthRequest;
import com.nikhilkarn.authwrapper.model.AuthResponse;
import com.nikhilkarn.authwrapper.model.OtpRequest;
import com.nikhilkarn.authwrapper.model.ApiResponse;
import com.nikhilkarn.authwrapper.service.LdapAuthService;
import com.nikhilkarn.authwrapper.service.OtpService;
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

    /**
     * Step 1: Authenticate using LDAP and trigger OTP.
     * @param request AuthRequest containing username and password
     * @return AuthResponse with session ID or failure
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthRequest request) {
        AuthResponse auth = ldapAuthService.authenticate(request.getUsername(), request.getPassword());
        otpService.sendOtp(auth.getEmail());  // Send OTP to LDAP email
        return ResponseEntity.ok(new ApiResponse(true, auth.getSessionId()));
    }

    /**
     * Step 2: Verify OTP sent to email or mobile.
     * @param otpRequest OtpRequest with username and OTP code
     * @return ApiResponse indicating verification result
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody OtpRequest otpRequest) {
        boolean valid = otpService.verifyOtp(otpRequest.getUsername(), otpRequest.getOtp());
        if (valid) {
            return ResponseEntity.ok(new ApiResponse(true, null));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse(false, null));
        }
    }
}
