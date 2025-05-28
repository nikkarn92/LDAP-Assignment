/**
 * POC Project for LDAP AUTH WRAPPER - ExternalOtpApiProvider Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.provider.ExternalOtpApiProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ExternalOtpApiProviderTest {

    private ExternalOtpApiProvider provider;
    private RestTemplate mockRestTemplate;

    @BeforeEach
    public void setup() throws Exception {
        provider = new ExternalOtpApiProvider();

        // Inject base URL config
        setField(provider, "baseUrl", "http://mock-otp-service.com");
        setField(provider, "sendPath", "/send-otp");
        setField(provider, "validatePath", "/validate-otp");

        // Replace RestTemplate with a mock
        mockRestTemplate = mock(RestTemplate.class);
        setField(provider, "restTemplate", mockRestTemplate);
    }

    @Test
    public void testSendOtp_Success() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Sent", HttpStatus.OK);
        when(mockRestTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(mockResponse);

        provider.send("testuser");

        verify(mockRestTemplate, times(1)).postForEntity(anyString(), any(), eq(String.class));
    }

    @Test
    public void testValidateOtp_Success() {
        ResponseEntity<Boolean> mockResponse = new ResponseEntity<>(true, HttpStatus.OK);
        when(mockRestTemplate.postForEntity(anyString(), any(), eq(Boolean.class))).thenReturn(mockResponse);

        boolean result = provider.validate("testuser", "123456");

        verify(mockRestTemplate, times(1)).postForEntity(anyString(), any(), eq(Boolean.class));
        assert result;
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
