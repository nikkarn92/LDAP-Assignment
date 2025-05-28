/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * External OTP API integration for send and validate operations.
 * Replace the base URL and endpoints with actual OTP service.
 */
@Component("externalOtpApiProvider")
public class ExternalOtpApiProvider implements OtpProvider {

    private static final Logger logger = LoggerFactory.getLogger(ExternalOtpApiProvider.class);

    @Value("${otp.api.base-url}")
    private String baseUrl;

    @Value("${otp.api.send-path:/send-otp}")
    private String sendPath;

    @Value("${otp.api.validate-path:/validate-otp}")
    private String validatePath;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void send(String username) {
        String url = baseUrl + sendPath;

        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                logger.warn("OTP send failed for {}: {}", username, response.getBody());
            } else {
                logger.info("OTP sent to user: {}", username);
            }
        } catch (Exception e) {
            logger.error("Error sending OTP to {}: {}", username, e.getMessage());
        }
    }

    @Override
    public boolean validate(String username, String otp) {
        String url = baseUrl + validatePath;

        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("otp", otp);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.postForEntity(url, request, Boolean.class);
            return response.getStatusCode() == HttpStatus.OK && Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            logger.error("Error validating OTP for {}: {}", username, e.getMessage());
            return false;
        }
    }
}
