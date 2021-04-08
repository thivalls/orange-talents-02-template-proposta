package com.br.zup.proposta.biometry.request;

import com.br.zup.proposta.biometry.model.Biometry;
import com.br.zup.proposta.card.model.Card;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

public class BiometryRequest {
    @NotBlank
    private String cardId;
    @NotBlank
    private String fingerprint;

    public BiometryRequest(@NotBlank String cardId, @NotBlank String fingerprint) {
        this.cardId = cardId;
        this.fingerprint = fingerprint;
    }

    public String getCardId() {
        return cardId;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    @Override
    public String toString() {
        return "BiometryRequest{" +
                "cardId='" + cardId + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                '}';
    }

    public Biometry toModel(Card card) {
        Assert.notNull(card, "Bug when search a valid card");
        return new Biometry(card, fingerprint);
    }
}
