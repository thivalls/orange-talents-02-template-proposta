package com.br.zup.proposta.card.model;

import com.br.zup.proposta.card.CardStatus;
import com.br.zup.proposta.proposal.Proposal;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public Card() {
    }

    public Card(String cardNumber, String owner, BigDecimal cardLimit, LocalDateTime createdAt, Proposal proposal) {
        Assert.hasLength(cardNumber, "Número de cartão é obrigatório");
        Assert.hasLength(owner, "Nome do dono do cartão é obrigatório");
        Assert.notNull(cardLimit, "Limite do cartão é obrigatório");
        Assert.state(cardLimit.compareTo(BigDecimal.ZERO) > 0, "Limite deve ser maior que 0");
        Assert.notNull(createdAt, "Momento de criação é obrigatório!");
        Assert.notNull(proposal, "Proposta é obrigatória!");

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

