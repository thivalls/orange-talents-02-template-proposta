package com.br.zup.proposta.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("It should create a new transaction with correct validation field")
    void test1() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest("878.234.560-01", "Fulano", 1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(transactionRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}