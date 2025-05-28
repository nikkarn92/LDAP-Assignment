/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class to hold LDAP properties from application.yml.
 */
@Configuration
@ConfigurationProperties(prefix = "ldap")
public class LdapConfig {

    private List<String> urls;
    private String baseDn;
    private String bindDn;
    private String bindPassword;
    private String userSearchFilter;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
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

    public String getUserSearchFilter() {
        return userSearchFilter;
    }

    public void setUserSearchFilter(String userSearchFilter) {
        this.userSearchFilter = userSearchFilter;
    }
}
