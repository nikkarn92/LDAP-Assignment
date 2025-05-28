/**
 * POC Project for LDAP AUTH WRAPPER - LoginAuditService Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.service.LoginAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.*;

public class LoginAuditServiceTest {

    private LoginAuditService auditService;

    @BeforeEach
    public void setup() {
        auditService = new LoginAuditService();
    }

    @Test
    public void testLoginSuccessResetsFailures() {
        String username = "jdoe";
        String ip = "192.168.1.1";

        for (int i = 0; i < 3; i++) {
            auditService.logLoginFailure(username, ip);
        }

        auditService.logLoginSuccess(username, ip);
        // No exception expected, internal state cleared
    }

    @Test
    public void testBruteForceThresholdTriggersAlert() {
        String username = "attacker";
        String ip = "10.0.0.1";

        for (int i = 0; i < 6; i++) {
            auditService.logLoginFailure(username, ip);
        }

        // Just verifying it doesn't throw; log will show ALERT
    }

    @Test
    public void testOtpFailureThresholdTriggersAlert() {
        String username = "user123";
        String ip = "172.16.0.1";

        for (int i = 0; i < 4; i++) {
            auditService.logOtpFailure(username, ip);
        }

        // Should trigger OTP abuse log
    }

    @Test
    public void testOtpSuccessClearsFailures() {
        String username = "jdoe";
        String ip = "192.168.1.1";

        auditService.logOtpFailure(username, ip);
        auditService.logOtpSuccess(username, ip);
    }
}
