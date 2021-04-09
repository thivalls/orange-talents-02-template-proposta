package com.br.zup.proposta.card.response;

import com.br.zup.proposta.card.model.Card;
import com.br.zup.proposta.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardResponse {

    @JsonProperty("id")
    private String cardNumber;

    @JsonProperty("emitidoEm")
    private LocalDateTime createdAt;

    @JsonProperty("idProposta")
    private String proposalId;

    @JsonProperty("titular")
    private String owner;

    @JsonProperty("limite")
    private BigDecimal cardLimit;

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getProposalId() {
        return proposalId;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public Card toModel(Proposal proposal) {
        Assert.state(proposal != null, "Proposal can not be null");
        return new Card(cardNumber, owner, cardLimit, createdAt, proposal);
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "cardNumber='" + cardNumber + '\'' +
                ", createdAt=" + createdAt +
                ", proposalId='" + proposalId + '\'' +
                ", owner='" + owner + '\'' +
                ", cardLimit=" + cardLimit +
                '}';
    }
}
