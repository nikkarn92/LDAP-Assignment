/**
 * POC Project for LDAP AUTH WRAPPER - LdapAuthService Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class LdapAuthServiceTest {

    @InjectMocks
    private com.nikhilkarn.authwrapper.service.LdapAuthService ldapAuthService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Inject dummy config values for testing
        TestUtils.setField(ldapAuthService, "ldapUrl", "ldap://localhost:389");
        TestUtils.setField(ldapAuthService, "baseDn", "dc=example,dc=com");
        TestUtils.setField(ldapAuthService, "bindDn", "cn=admin,dc=example,dc=com");
        TestUtils.setField(ldapAuthService, "bindPassword", "admin");
        TestUtils.setField(ldapAuthService, "searchFilter", "uid={0}");
    }

    @Test
    public void testAuthenticate_InvalidUser_ThrowsException() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            ldapAuthService.authenticate("invaliduser", "wrongpass");
        });

        assertTrue(ex.getMessage().contains("LDAP authentication failed"));
    }
}
