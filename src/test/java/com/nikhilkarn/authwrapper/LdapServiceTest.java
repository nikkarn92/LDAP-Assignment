package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.model.LdapConfig;
import com.nikhilkarn.authwrapper.service.LdapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LdapServiceTest {

    private LdapService ldapService;

    @BeforeEach
    void setup() {
        ldapService = new LdapService();
    }

    @Test
    void saveAndGetTenant() {
        LdapConfig config = new LdapConfig();
        config.setTenantId("test-tenant");
        ldapService.saveOrUpdateLdapConfig(config);

        assertTrue(ldapService.getAllTenantIds().contains("test-tenant"));
    }
}
