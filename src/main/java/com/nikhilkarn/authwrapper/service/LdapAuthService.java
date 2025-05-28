/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.service;

import com.nikhilkarn.authwrapper.model.AuthResponse;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.directory.DirContext;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Service responsible for performing LDAP authentication.
 */
@Service
public class LdapAuthService {

    @Value("${ldap.urls[0]}")
    private String ldapUrl;

    @Value("${ldap.base-dn}")
    private String baseDn;

    @Value("${ldap.bind-dn}")
    private String bindDn;

    @Value("${ldap.bind-password}")
    private String bindPassword;

    @Value("${ldap.user-search-filter}")
    private String searchFilter;

    /**
     * Authenticate the user and extract email from LDAP.
     * @param username user's UID
     * @param password user's password
     * @return AuthResponse with sessionId and email
     */
    public AuthResponse authenticate(String username, String password) {
        DirContext serviceCtx = null;
        DirContext userCtx = null;

        try {
            // Step 1: Bind with service account
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, ldapUrl);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, bindDn);
            env.put(Context.SECURITY_CREDENTIALS, bindPassword);

            serviceCtx = new InitialDirContext(env);

            // Step 2: Search for user DN and get mail attribute
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(new String[]{"mail"}); // fetch mail

            NamingEnumeration<SearchResult> results =
                    serviceCtx.search(baseDn, searchFilter.replace("{0}", username), controls);

            if (!results.hasMore()) {
                throw new RuntimeException("User not found in LDAP.");
            }

            SearchResult result = results.next();
            String userDn = result.getNameInNamespace();
            Attributes attrs = result.getAttributes();
            String email = (attrs.get("mail") != null) ? (String) attrs.get("mail").get() : null;

            // Step 3: Bind with user DN and password
            Hashtable<String, String> userEnv = new Hashtable<>(env);
            userEnv.put(Context.SECURITY_PRINCIPAL, userDn);
            userEnv.put(Context.SECURITY_CREDENTIALS, password);
            userCtx = new InitialDirContext(userEnv);

            return new AuthResponse(UUID.randomUUID().toString(), email);

        } catch (NamingException ex) {
            throw new RuntimeException("LDAP authentication failed: " + ex.getMessage(), ex);
        } finally {
            try {
                if (serviceCtx != null) serviceCtx.close();
                if (userCtx != null) userCtx.close();
            } catch (NamingException ignored) {
            }
        }
    }
}
