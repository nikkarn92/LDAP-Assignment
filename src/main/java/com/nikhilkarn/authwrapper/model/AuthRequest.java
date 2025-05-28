/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.model;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for handling authentication requests.
 */
public class AuthRequest {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;

    private String ipAddress;

    public AuthRequest() {}

    public AuthRequest(String username, String password, String ipAddress) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
