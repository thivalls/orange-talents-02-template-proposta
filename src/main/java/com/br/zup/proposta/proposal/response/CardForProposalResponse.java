package com.br.zup.proposta.proposal.response;

import com.br.zup.proposta.card.CardStatus;
import com.br.zup.proposta.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CardForProposalResponse {
    @JsonProperty("card_number")
    private String cardNUmber;
    @JsonProperty("status")
    private CardStatus cardStatus;
    @JsonProperty("created")
    private LocalDateTime cardCreatedAt;

    public CardForProposalResponse(Proposal proposal) {
        this.cardNUmber = proposal.getCard().getCardNumber();
        this.cardStatus = proposal.getCard().getStatus();
        this.cardCreatedAt = proposal.getCard().getCreatedAt();
    }
}
