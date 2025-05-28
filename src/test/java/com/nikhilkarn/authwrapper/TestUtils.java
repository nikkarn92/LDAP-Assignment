package com.nikhilkarn.authwrapper;

import java.lang.reflect.Field;

/**
 * Utility to inject test field values using reflection
 */
public class TestUtils {
    public static void setField(Object target, String name, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + name, e);
        }
    }
}
