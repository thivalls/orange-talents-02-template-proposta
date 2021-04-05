package com.br.zup.proposta.proposal;

import com.br.zup.proposta.card.Card;
import com.br.zup.proposta.proposal.transaction.TransactionStatus;

public class ProposalStatusResponse {
    private Long id;
    private TransactionStatus status;
    private Card card;

    public ProposalStatusResponse(Proposal proposal) {
        this.id = proposal.getId();
        this.status = proposal.getStatus();
        this.card = proposal.getCard();
    }

    @Override
    public String toString() {
        return "ProposalStatusResponse{" +
                "id=" + id +
                ", status=" + status +
                ", card=" + card +
                '}';
    }
}
