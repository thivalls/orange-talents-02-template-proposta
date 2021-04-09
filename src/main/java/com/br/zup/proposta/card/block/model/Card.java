package com.br.zup.proposta.card.model;

import com.br.zup.proposta.card.CardStatus;
import com.br.zup.proposta.proposal.Proposal;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cards")
public class Card {

    @Id
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String cardNumber;

    @NotBlank
    @Column(nullable = false)
    private String owner;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal cardLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status = CardStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "proposal_id", nullable = false)
    private Proposal proposal;

    @Deprecated
    public Card() {
    }

    public Card(String cardNumber, String owner, BigDecimal cardLimit, LocalDateTime createdAt, Proposal proposal) {
        Assert.hasLength(cardNumber, "Card number is required!");
        Assert.hasLength(owner, "Card owner is required");
        Assert.notNull(cardLimit, "Limit is required");
        Assert.state(cardLimit.compareTo(BigDecimal.ZERO) > 0, "Limit must be greater than 0");
        Assert.notNull(createdAt, "The time of creation is required!");
        Assert.notNull(proposal, "Proposal is required!");

        this.id = cardNumber;
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.cardLimit = cardLimit;
        this.createdAt = createdAt;
        this.proposal = proposal;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Proposal getProposal() {
        return proposal;
    }


    public CardStatus getStatus() {
        return status;
    }

    public void blockCard() {
        this.status = CardStatus.BLOCKED;
    }
}

