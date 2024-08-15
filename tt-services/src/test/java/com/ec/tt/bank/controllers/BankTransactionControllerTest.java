package com.ec.tt.bank.controllers;

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
class BankTransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationTest authenticationTest;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

//    @Test
//    void create() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movimientos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "    \"date\": 1723093200000,\n" +
//                                "    \"transactionType\": \"Ahorro\",\n" +
//                                "    \"amount\": 10,\n" +
//                                "    \"accountId\": 1\n" +
//                                "}")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
//                .andExpect(status().isCreated());
//    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"bankTransactionId\": 10000,\n" +
                                "    \"date\": 1723093200000,\n" +
                                "    \"transactionType\": \"Ahorro\",\n" +
                                "    \"amount\": -400,\n" +
                                "    \"accountId\": 1\n" +
                                "}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movimientos/10000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    void findReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimientos/reportes?initialDate=1691643599000&endDate=1723265999000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationTest.obtainAccessToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}