package com.nikhilkarn.authwrapper.controller;

import com.nikhilkarn.authwrapper.model.Client;
import com.nikhilkarn.authwrapper.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API to register and list clients that are authorized to use the authentication APIs.
 */
@RestController
@RequestMapping("/clients")
@Tag(name = "Client Management API", description = "Register and manage API clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    @Operation(summary = "Register a new API client")
    public ResponseEntity<String> registerClient(@RequestBody Client client) {
        clientService.register(client);
        return ResponseEntity.ok("Client registered successfully: " + client.getClientId());
    }

    @GetMapping("/list")
    @Operation(summary = "List all registered API clients")
    public ResponseEntity<?> listClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
