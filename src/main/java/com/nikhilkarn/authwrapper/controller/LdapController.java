package com.nikhilkarn.authwrapper.controller;

import com.nikhilkarn.authwrapper.model.LdapConfig;
import com.nikhilkarn.authwrapper.service.LdapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API for managing tenant-specific LDAP configurations.
 */
@RestController
@RequestMapping("/ldap")
@Tag(name = "LDAP Configuration API", description = "Manage LDAP settings per tenant")
public class LdapController {

    @Autowired
    private LdapService ldapService;

    @PostMapping("/configure")
    @SecurityRequirement(name = "apiKeyAuth")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Configure or update LDAP settings for a tenant")
    public ResponseEntity<String> configureLdap(@RequestBody LdapConfig config) {
        ldapService.saveOrUpdateLdapConfig(config);
        return ResponseEntity.ok("LDAP configuration saved for tenant: " + config.getTenantId());
    }

    @GetMapping("/list")
    @Operation(summary = "List all tenant IDs that have LDAP configurations")
    public ResponseEntity<List<String>> listTenants() {
        List<String> tenantIds = ldapService.getAllTenantIds();
        return ResponseEntity.ok(tenantIds);
    }
}
