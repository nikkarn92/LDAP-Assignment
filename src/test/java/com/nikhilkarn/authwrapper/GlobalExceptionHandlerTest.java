/**
 * POC Project for LDAP AUTH WRAPPER - GlobalExceptionHandler Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.model.ApiResponse;
import com.nikhilkarn.authwrapper.exception.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleValidationErrors() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult("authRequest", "authRequest");
        bindingResult.addError(new FieldError("authRequest", "username", "must not be blank"));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiResponse> response = handler.handleValidationErrors(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getMessage().contains("username"));
    }

    @Test
    public void testHandleConstraintViolations() {
        ConstraintViolationException ex = new ConstraintViolationException(Set.of(new DummyViolation()));
        ResponseEntity<ApiResponse> response = handler.handleConstraintViolations(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
    }

    @Test
    public void testHandleGenericException() {
        Exception ex = new RuntimeException("Something went wrong");
        ResponseEntity<ApiResponse> response = handler.handleGenericException(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getMessage().contains("Something went wrong"));
    }

    static class DummyViolation implements ConstraintViolation<Object> {

        @Override public String getMessage() { return "must not be null"; }
        @Override public String getMessageTemplate() { return null; }
        @Override public Object getRootBean() { return null; }
        @Override public Class<Object> getRootBeanClass() { return Object.class; }
        @Override public Object getLeafBean() { return null; }
        @Override public Object[] getExecutableParameters() { return new Object[0]; }
        @Override public Object getExecutableReturnValue() { return null; }
        @Override public jakarta.validation.Path getPropertyPath() { return () -> null; }
        @Override public Object getInvalidValue() { return null; }
        @Override public ConstraintDescriptor<?> getConstraintDescriptor() { return null; }
        @Override public <U> U unwrap(Class<U> type) { throw new UnsupportedOperationException(); }
    }
}
