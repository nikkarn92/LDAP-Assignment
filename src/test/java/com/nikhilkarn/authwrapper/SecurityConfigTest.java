/**
 * POC Project for LDAP AUTH WRAPPER - SecurityConfig Test
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    public void testApiAccessIsPermitted() throws Exception {
        mockMvc.perform(get("/api/authenticate"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNonApiRequestIsRestricted() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isUnauthorized()); // or Forbidden if auth is partially configured
    }*/
}
