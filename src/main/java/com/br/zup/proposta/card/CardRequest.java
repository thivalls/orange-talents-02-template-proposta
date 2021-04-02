package com.br.zup.proposta.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardRequest {

    @JsonProperty("documento")
   private String document;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("idProposta")
    private String proposalId;

    public CardRequest(String document, String name, String proposalId) {
        this.document = document;
        this.name = name;
        this.proposalId = proposalId;
    }
}
