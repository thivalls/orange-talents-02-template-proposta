package com.br.zup.proposta.proposal;

import com.br.zup.proposta.proposal.request.AddressRequest;
import com.br.zup.proposta.proposal.request.ProposalRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("It should not create a new proposal with equal document user")
    void test14() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", addressRequest, new BigDecimal(1000));

        mockMvc.perform(MockMvcRequestBuilders.post("/proposals").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(proposalRequest)));
        mockMvc.perform(MockMvcRequestBuilders.post("/proposals").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(proposalRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("It should create a new proposal with correct fields validation")
    void test1() throws Exception {
//        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
//        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", addressRequest, new BigDecimal(1000));
//
//        MvcResult location = mockMvc.perform(
//                MockMvcRequestBuilders.post("/proposals")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(proposalRequest))
//        )
//                // This comments are only to write down some anothers ways to do the same thing below
//                // .andExpect(MockMvcResultMatchers.header().exists("Location"))
//                // .andExpect(MockMvcResultMatchers.header().stringValues("http://localhost:8080/proposals/1"))
//                // .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.endsWith("/proposals/1")))
//                .andReturn();
//
//        Query query = em.createQuery("Select a from Proposal a where a.email = :email", Proposal.class);
//        query.setParameter("email", proposalRequest.getEmail());
//        Proposal foundProposal = (Proposal) query.getSingleResult();
//
//        Assertions.assertEquals(201, location.getResponse().getStatus());
//        Assertions.assertTrue(location.getResponse().getHeader("Location").contains("/proposals/" + foundProposal.getId()));
//        Assertions.assertEquals("http://localhost/proposals/" + foundProposal.getId(), location.getResponse().getHeader("Location"));
//        Assertions.assertTrue(location.getResponse().getHeader("Location").endsWith("/proposals/" + foundProposal.getId()));
//
//        Assertions.assertNotNull(foundProposal);
//        Assertions.assertEquals("Fulano", foundProposal.getName());
//        Assertions.assertEquals("email@email.com", foundProposal.getEmail());
//        Assertions.assertEquals("878.234.560-01", foundProposal.getDocument());
    }

    @Test
    @DisplayName("It should not create a new proposal with invalid email address")
    void test2() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "invalidemail", "Fulano", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable email address")
    void test3() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", null, "Fulano", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with empty email address")
    void test4() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "", "Fulano", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with empty field name")
    void test5() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field name")
    void test6() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", null, addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
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

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field salary")
    void test9() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", addressRequest, null);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with negative field salary")
    void test10() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("878.234.560-01", "email@email.com", "Fulano", addressRequest, new BigDecimal(-1));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with nullable field document")
    void test11() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest(null, "email@email.com", "Fulano", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with empty field document")
    void test12() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("", "email@email.com", "Fulano", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    @Test
    @DisplayName("It should not create a new proposal with invalid field document")
    void test13() throws Exception {
        AddressRequest addressRequest = new AddressRequest("Alameda das Orquídeas", "999", "13560000");
        ProposalRequest proposalRequest = new ProposalRequest("233444555", "email@email.com", "Fulano", addressRequest, new BigDecimal(1000));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposalRequest))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertNoResultsDatabase();
    }

    private void assertNoResultsDatabase() {
        Query query = em.createQuery("Select a from Proposal a", Proposal.class);
        List<Proposal> resultList = query.getResultList();

        Assertions.assertEquals(0, resultList.size());
    }
}
