package com.nikhilkarn.authwrapper.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Registered client credentials for accessing the authentication APIs")
public class Client {

    @Schema(description = "Client identifier", example = "my-client-app")
    private String clientId;

    @Schema(description = "Client secret (API key or password)", example = "s3cr3tKey123")
    private String clientSecret;

    // Constructors
    public Client() {}

    public Client(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    // Getters & Setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
