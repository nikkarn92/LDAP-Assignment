package com.nikhilkarn.authwrapper.service;

import com.nikhilkarn.authwrapper.model.LdapConfig;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to handle multi-tenant LDAP authentication and configuration.
 */
@Service
public class LdapService {

    // In-memory map to store tenant LDAP configs
    private final Map<String, LdapConfig> tenantConfigs = new ConcurrentHashMap<>();

    /**
     * Save or update a tenant's LDAP configuration.
     */
    public void saveOrUpdateLdapConfig(LdapConfig config) {
        tenantConfigs.put(config.getTenantId(), config);
    }

    /**
     * Get all tenant IDs configured.
     */
    public List<String> getAllTenantIds() {
        return new ArrayList<>(tenantConfigs.keySet());
    }

    /**
     * Authenticate user credentials using LDAP for a given tenant.
     */
    public boolean authenticate(String tenantId, String username, String password) {
        LdapConfig config = tenantConfigs.get(tenantId);
        if (config == null) return false;

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, config.getLdapUrl());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, config.getBindDn());
        env.put(Context.SECURITY_CREDENTIALS, config.getBindPassword());

        try {
            DirContext ctx = new InitialDirContext(env);

            String searchFilter = config.getUserSearchFilter().replace("{0}", username);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> results = ctx.search(config.getUserSearchBase(), searchFilter, controls);
            if (results.hasMore()) {
                SearchResult result = results.next();
                String userDn = result.getNameInNamespace();

                // Now try binding with user credentials
                Hashtable<String, String> userEnv = new Hashtable<>(env);
                userEnv.put(Context.SECURITY_PRINCIPAL, userDn);
                userEnv.put(Context.SECURITY_CREDENTIALS, password);

                new InitialDirContext(userEnv).close(); // if no exception, success
                return true;
            }
        } catch (Exception ex) {
            System.err.println("LDAP Authentication failed for tenant [" + tenantId + "]: " + ex.getMessage());
        }

        return false;
    }

    /**
     * Fetch the user's email from LDAP after successful bind.
     */
    public String fetchEmail(String tenantId, String username) {
        LdapConfig config = tenantConfigs.get(tenantId);
        if (config == null) return null;

        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, config.getLdapUrl());
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, config.getBindDn());
            env.put(Context.SECURITY_CREDENTIALS, config.getBindPassword());

            DirContext ctx = new InitialDirContext(env);
            String searchFilter = config.getUserSearchFilter().replace("{0}", username);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> results = ctx.search(config.getUserSearchBase(), searchFilter, controls);
            if (results.hasMore()) {
                Attributes attributes = results.next().getAttributes();
                return (String) attributes.get(config.getEmailAttribute()).get();
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch email from LDAP: " + e.getMessage());
        }

        return null;
    }
}
