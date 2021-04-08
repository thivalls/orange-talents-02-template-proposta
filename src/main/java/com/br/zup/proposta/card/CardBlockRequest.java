package com.br.zup.proposta.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CardBlockRequest {
    @NotBlank
    @JsonProperty("sistemaResponsavel")
    private String emitter;

    @Deprecated
    public CardBlockRequest() {
    }

    public CardBlockRequest(@NotBlank String emitter) {
        this.emitter = emitter;
    }

    public String getEmitter() {
        return emitter;
    }

    public CardBlock toModel(String userIp, String userAgent, Card card) {
        return new CardBlock( userIp, userAgent, card);
    }
}
