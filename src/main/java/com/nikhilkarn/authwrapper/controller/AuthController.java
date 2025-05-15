package com.nikhilkarn.authwrapper.controller;

import com.nikhilkarn.authwrapper.model.AuthRequest;
import com.nikhilkarn.authwrapper.model.OtpRequest;
import com.nikhilkarn.authwrapper.service.LdapService;
import com.nikhilkarn.authwrapper.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles authentication and OTP verification requests.
 */
@RestController
@RequestMapping("/{tenantId}")
@Tag(name = "Authentication API", description = "LDAP + OTP-based MFA endpoints")
public class AuthController {

    @Autowired
    private LdapService ldapService;

    @Autowired
    private OtpService otpService;

    @SecurityRequirement(name = "apiKeyAuth")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user and send OTP to email")
    public ResponseEntity<String> authenticate(
            @PathVariable String tenantId,
            @RequestBody AuthRequest request) {

        boolean isAuthenticated = ldapService.authenticate(tenantId, request.getUsername(), request.getPassword());

        if (!isAuthenticated) {
            return ResponseEntity.status(401).body("LDAP authentication failed");
        }

        String userEmail = ldapService.fetchEmail(tenantId, request.getUsername());
        String otp = otpService.generateOtp(userEmail);

        otpService.sendOtpEmail(userEmail, otp);

        return ResponseEntity.ok("OTP sent to registered email.");
    }

    @SecurityRequirement(name = "apiKeyAuth")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/verify-otp")
    @Operation(summary = "Verify the OTP to complete login")
    public ResponseEntity<String> verifyOtp(
            @PathVariable String tenantId,
            @RequestBody OtpRequest request) {

        boolean valid = otpService.verifyOtp(request.getEmail(), request.getOtp());

        if (!valid) {
            return ResponseEntity.status(403).body("Invalid or expired OTP");
        }

        return ResponseEntity.ok("Authentication successful!");
    }
}
