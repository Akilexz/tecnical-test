package com.ec.tt.bank.controllers;

import com.ec.tt.security.AuthenticationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationTest authenticationTest;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

//    @Test
//    void create() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cuentas")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "    \"accountNumber\": 200,\n" +
//                                "    \"accountType\": \"Ahorro\",\n" +
//                                "    \"initialBalance\": 200,\n" +
//                                "    \"customerId\": 2\n" +
//                                "}")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
//                .andExpect(status().isCreated());
//    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 10000,\n" +
                                "    \"accountNumber\": 201,\n" +
                                "    \"accountType\": \"Ahorro\",\n" +
                                "    \"initialBalance\": null,\n" +
                                "    \"customerId\": 2\n" +
                                "}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cuentas/10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk());
    }
}