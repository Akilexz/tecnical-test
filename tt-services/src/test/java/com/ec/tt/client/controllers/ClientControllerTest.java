package com.ec.tt.client.controllers;

import com.ec.tt.security.AuthenticationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationTest authenticationTest;

    @Test
    void findAll() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

//    @Test
//    void create() {
//    }

    @Test
    void update() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"customerId\": 10000,\n" +
                                "    \"personId\": 1,\n" +
                                "    \"password\": \"12345\"\n" +
                                "}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clientes/10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk());
    }
}