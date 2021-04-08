package com.br.zup.proposta.card;


import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockCardResponse {
    @JsonProperty("resultado")
    private String responseStatus;

    public BlockCardResponse() {
        this.responseStatus = responseStatus;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    @Override
    public String toString() {
        return "BlockCardResponse{" +
                "responseStatus=" + responseStatus +
                '}';
    }
}
