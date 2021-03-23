package com.br.zup.proposta.proposal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.util.regex.Matcher;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("It should create a new proposal with correct fields validation")
    void test1() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        // URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        MvcResult location = mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().stringValues("http://localhost:8080/proposals/1"))
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.endsWith("/proposals/1")))
                .andReturn();

        Assertions.assertEquals("http://localhost/proposals/1", location.getResponse().getHeader("Location"));
        Assertions.assertTrue(location.getResponse().getHeader("Location").contains("/proposals/1"));

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNotNull(proposal);
        Assertions.assertEquals(1, proposal.getId());
        Assertions.assertEquals("Fulano", proposal.getName());
        Assertions.assertEquals("email@email.com", proposal.getEmail());
        Assertions.assertEquals("878.234.560-01", proposal.getDocument());
    }

    @Test
    @DisplayName("It should not create a new proposal with invalid email address")
    void test2() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "invalidemail", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable email address")
    void test3() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", null, "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with empty email address")
    void test4() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with empty field name")
    void test5() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field name")
    void test6() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", null, "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field address")
    void test7() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", null, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with empty field address")
    void test8() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", "", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field salary")
    void test9() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", "Alameda das Orquídeas, 1080", null);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with negative field salary")
    void test10() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(-1));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field document")
    void test11() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest(null, "email@email.com", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with empty field document")
    void test12() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("", "email@email.com", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }

    @Test
    @DisplayName("It should not create a new proposal with invalid field document")
    void test13() throws Exception {
        ProposalRequest proposalRequest = new ProposalRequest("233444555", "email@email.com", "Fulano", "Alameda das Orquídeas, 1080", new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        Proposal proposal = em.find(Proposal.class, 1L);

        Assertions.assertNull(proposal);
    }
}
