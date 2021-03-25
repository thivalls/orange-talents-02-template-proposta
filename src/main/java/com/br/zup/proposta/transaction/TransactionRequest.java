package com.br.zup.proposta.transaction;

import com.br.zup.proposta.shared.validation.CpfOrCnpj;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionRequest {

    @CpfOrCnpj
    @NotBlank
    @JsonProperty("documento")
    private String document;

    @NotBlank
    @JsonProperty("nome")
    private String name;

    @NotNull
    @JsonProperty("idProposta")
    private Long proposalId;

    @NotNull
    private TransactionStatus status;

    public TransactionRequest(@NotBlank String document, @NotBlank String name, @NotNull Long proposalId) {
        this.document = document;
        this.name = name;
        this.proposalId = proposalId;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public Long getProposalId() {
        return proposalId;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", proposalId=" + proposalId +
                '}';
    }
}