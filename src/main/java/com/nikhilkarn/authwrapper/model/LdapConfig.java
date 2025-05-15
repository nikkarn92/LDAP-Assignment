package com.nikhilkarn.authwrapper.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "LDAP Configuration for a specific tenant")
public class LdapConfig {

    @Schema(description = "Unique tenant identifier", example = "acme-corp")
    private String tenantId;

    @Schema(description = "LDAP server URL (e.g. ldaps://ldap.acme.com:636)", example = "ldaps://ldap.example.com:636")
    private String ldapUrl;

    @Schema(description = "Bind DN used to search for users", example = "cn=admin,dc=example,dc=com")
    private String bindDn;

    @Schema(description = "Password for the bind DN", example = "admin-password")
    private String bindPassword;

    @Schema(description = "LDAP base where users are located", example = "ou=users,dc=example,dc=com")
    private String userSearchBase;

    @Schema(description = "Search filter for finding the user", example = "(uid={0})")
    private String userSearchFilter;

    @Schema(description = "LDAP attribute that holds the user's email", example = "mail")
    private String emailAttribute;

    // Constructors
    public LdapConfig() {}

    public LdapConfig(String tenantId, String ldapUrl, String bindDn, String bindPassword,
                      String userSearchBase, String userSearchFilter, String emailAttribute) {
        this.tenantId = tenantId;
        this.ldapUrl = ldapUrl;
        this.bindDn = bindDn;
        this.bindPassword = bindPassword;
        this.userSearchBase = userSearchBase;
        this.userSearchFilter = userSearchFilter;
        this.emailAttribute = emailAttribute;
    }

    // Getters & Setters
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLdapUrl() {
        return ldapUrl;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public String getBindDn() {
        return bindDn;
    }

    public void setBindDn(String bindDn) {
        this.bindDn = bindDn;
    }

    public String getBindPassword() {
        return bindPassword;
    }

    public void setBindPassword(String bindPassword) {
        this.bindPassword = bindPassword;
    }

    public String getUserSearchBase() {
        return userSearchBase;
    }

    public void setUserSearchBase(String userSearchBase) {
        this.userSearchBase = userSearchBase;
    }

    public String getUserSearchFilter() {
        return userSearchFilter;
    }

    public void setUserSearchFilter(String userSearchFilter) {
        this.userSearchFilter = userSearchFilter;
    }

    public String getEmailAttribute() {
        return emailAttribute;
    }

    public void setEmailAttribute(String emailAttribute) {
        this.emailAttribute = emailAttribute;
    }
}
