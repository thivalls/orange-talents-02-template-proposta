package com.br.zup.proposta.travel.model;

import com.br.zup.proposta.card.model.Card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card_notices")
public class CardNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Card card;

    @NotBlank
    private String result;

    @Deprecated
    public CardNotice() {
    }

    public CardNotice(@NotNull Card card, @NotBlank String result) {
        this.card = card;
        this.result = result;
    }
}
