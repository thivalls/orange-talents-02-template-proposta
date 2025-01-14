package com.br.zup.proposta.proposal.transaction;

import com.br.zup.proposta.proposal.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse {

    @JsonProperty("documento")
    private String document;

    @JsonProperty("idProposta")
    private Long proposalId;

    @JsonProperty("resultadoSolicitacao")
    private String status;

    public TransactionResponse(String document, Long proposalId, String status) {
        this.document = document;
        this.proposalId = proposalId;
        this.status = status;
    }

    public String getTransactionId() {
        return document;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "document='" + document + '\'' +
                ", proposalId=" + proposalId +
                ", status='" + status + '\'' +
                '}';
    }

    public ProposalStatus resolveEnum() {
        if("SEM_RESTRICAO".equals(status)) {
            return ProposalStatus.ELIGIBLE;
        }
        return  ProposalStatus.NOT_ELIGIBLE;
    }
}