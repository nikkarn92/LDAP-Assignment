package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.util.LoggerUtil;
import org.junit.jupiter.api.Test;

public class LoggerUtilTest {

    @Test
    void logInfoShouldNotThrow() {
        LoggerUtil.logInfo(LoggerUtilTest.class, "Info message");
        LoggerUtil.logWarn(LoggerUtilTest.class, "Warn message");
        LoggerUtil.logError(LoggerUtilTest.class, "Error message", new RuntimeException("Test"));
    }
}
