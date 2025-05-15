package com.nikhilkarn.authwrapper.service;

import com.nikhilkarn.authwrapper.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to manage API client registrations and validations.
 */
@Service
public class ClientService {

    // In-memory store for client registrations
    private final ConcurrentHashMap<String, Client> clientRegistry = new ConcurrentHashMap<>();

    /**
     * Register a new client.
     */
    public void register(Client client) {
        clientRegistry.put(client.getClientId(), client);
    }

    /**
     * Validate a client's credentials.
     */
    public boolean isValidClient(String clientId, String clientSecret) {
        Client registered = clientRegistry.get(clientId);
        return registered != null && registered.getClientSecret().equals(clientSecret);
    }

    /**
     * Return all registered clients (for admin/debug use).
     */
    public List<Client> getAllClients() {
        return new ArrayList<>(clientRegistry.values());
    }
}
