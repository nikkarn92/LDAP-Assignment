package com.nikhilkarn.authwrapper.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication request containing user credentials")
public class AuthRequest {

    @Schema(description = "Username to authenticate", example = "john.doe")
    private String username;

    @Schema(description = "User's password", example = "Secret@123")
    private String password;

    // Constructors
    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & Setters
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
}
