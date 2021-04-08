package com.br.zup.proposta.proposal.response;

import com.br.zup.proposta.proposal.Address;
import com.br.zup.proposta.proposal.Proposal;
import com.br.zup.proposta.proposal.transaction.TransactionStatus;

import java.math.BigDecimal;

public class ProposalStatusResponse {
    private Long id;
    private TransactionStatus status;
    private String document;
    private String email;
    private String name;
    private Address addressRequest;
    private BigDecimal salary;
    private CardForProposalResponse card;

    public ProposalStatusResponse(Proposal proposal) {
        this.id = proposal.getId();
        this.status = proposal.getStatus();
        this.document = proposal.getDocument();
        this.email = proposal.getEmail();
        this.name = proposal.getName();
        this.addressRequest = proposal.getAddress();
        this.salary = proposal.getSalary();
        this.card = null;
        if (proposal.getCard() != null) {
            this.card = new CardForProposalResponse(proposal);
        }
    }

    public Long getId() {
        return id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public CardForProposalResponse getCard() {
        return card;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Address getAddressRequest() {
        return addressRequest;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ProposalStatusResponse{" +
                "id=" + id +
                ", status=" + status +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", addressRequest=" + addressRequest +
                ", salary=" + salary +
                ", card=" + card +
                '}';
    }
}
