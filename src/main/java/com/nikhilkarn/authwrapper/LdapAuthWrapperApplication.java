/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the LDAP Authentication Wrapper.
 * This application exposes REST APIs for authenticating users via external LDAP
 * and managing OTP-based MFA via pluggable external services.
 */
@SpringBootApplication
public class LdapAuthWrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdapAuthWrapperApplication.class, args);
    }
}