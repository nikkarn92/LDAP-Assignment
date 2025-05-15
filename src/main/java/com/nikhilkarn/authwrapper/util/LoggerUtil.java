package com.nikhilkarn.authwrapper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Centralized logging utility for consistent and structured logs.
 */
public class LoggerUtil {

    public static void logInfo(Class<?> clazz, String message) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message);
    }

    public static void logWarn(Class<?> clazz, String message) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.warn(message);
    }

    public static void logError(Class<?> clazz, String message, Throwable throwable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, throwable);
    }

    public static void logStructured(Class<?> clazz, String eventName, Map<String, Object> data) {
        Logger logger = LoggerFactory.getLogger(clazz);
        StringBuilder logMessage = new StringBuilder("event=" + eventName);

        data.forEach((key, value) -> logMessage.append(" ").append(key).append("=").append(value));
        logger.info(logMessage.toString());
    }
}
