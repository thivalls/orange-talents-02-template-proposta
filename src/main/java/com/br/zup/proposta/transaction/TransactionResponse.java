package com.br.zup.proposta.transaction;

import com.br.zup.proposta.shared.validation.CpfOrCnpj;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionResponse {

    @JsonProperty("resultadoSolicitacao")
    private String transactionId;

    @JsonProperty("idProposta")
    private Long proposalId;

    public TransactionResponse(String transactionId, Long proposalId) {
        this.transactionId = transactionId;
        this.proposalId = proposalId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Long getProposalId() {
        return proposalId;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionId='" + transactionId + '\'' +
                ", proposalId=" + proposalId +
                '}';
    }
}