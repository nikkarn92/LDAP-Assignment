package com.nikhilkarn.authwrapper;

import com.nikhilkarn.authwrapper.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    void authenticate_shouldReturn401WhenInvalid() throws Exception {
        mockMvc.perform(post("/tenant/authenticate")
                .contentType("application/json")
                .content("{\"username\":\"test\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }*/
}
