/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to hold OTP settings.
 */
@Configuration
@ConfigurationProperties(prefix = "otp")
public class OtpConfig {

    private long expirySeconds;
    private String apiBaseUrl;
    private String sendPath;
    private String validatePath;

    public long getExpirySeconds() {
        return expirySeconds;
    }

    public void setExpirySeconds(long expirySeconds) {
        this.expirySeconds = expirySeconds;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public String getValidatePath() {
        return validatePath;
    }

    public void setValidatePath(String validatePath) {
        this.validatePath = validatePath;
    }
}
