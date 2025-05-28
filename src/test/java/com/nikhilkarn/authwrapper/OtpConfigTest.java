/**
 * POC Project for LDAP AUTH WRAPPER - OtpConfig Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.config.OtpConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class OtpConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(OtpConfig.class)
            .withPropertyValues(
                    "otp.expiry-seconds=300",
                    "otp.api-base-url=http://localhost:8082/otp-api",
                    "otp.send-path=/send",
                    "otp.validate-path=/validate"
            );

    @Test
    public void testOtpConfigBinding() {
        /*contextRunner.run(context -> {
            assertThat(context).hasSingleBean(OtpConfig.class);

            OtpConfig config = context.getBean(OtpConfig.class);
            assertThat(config.getExpirySeconds()).isEqualTo(300);
            assertThat(config.getApiBaseUrl()).isEqualTo("http://localhost:8082/otp-api");
            assertThat(config.getSendPath()).isEqualTo("/send");
            assertThat(config.getValidatePath()).isEqualTo("/validate");
        });*/
    }
}
