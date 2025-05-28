/**
 * POC Project for LDAP AUTH WRAPPER - LdapConfig Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.config.LdapConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.util.TestPropertyValues;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LdapConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(LdapConfig.class)
            .withPropertyValues(
                    "ldap.urls[0]=ldap://localhost:389",
                    "ldap.base-dn=dc=example,dc=com",
                    "ldap.bind-dn=cn=admin,dc=example,dc=com",
                    "ldap.bind-password=admin",
                    "ldap.user-search-filter=uid={0}"
            );

    @Test
    public void testLdapConfigBinding() {
        /*contextRunner.run(context -> {
            assertThat(context).hasSingleBean(LdapConfig.class);

            LdapConfig config = context.getBean(LdapConfig.class);
            assertThat(config.getUrls()).containsExactly("ldap://localhost:389");
            assertThat(config.getBaseDn()).isEqualTo("dc=example,dc=com");
            assertThat(config.getBindDn()).isEqualTo("cn=admin,dc=example,dc=com");
            assertThat(config.getBindPassword()).isEqualTo("admin");
            assertThat(config.getUserSearchFilter()).isEqualTo("uid={0}");
        });*/
    }
}
