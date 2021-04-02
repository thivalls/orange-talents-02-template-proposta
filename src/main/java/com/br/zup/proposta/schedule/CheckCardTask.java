package com.br.zup.proposta.schedule;

import com.br.zup.proposta.card.Card;
import com.br.zup.proposta.card.CardRepository;
import com.br.zup.proposta.http.client.feign.CheckCardNumberClient;
import com.br.zup.proposta.card.CardResponse;
import com.br.zup.proposta.card.CardRequest;
import com.br.zup.proposta.proposal.Proposal;
import com.br.zup.proposta.proposal.ProposalRepository;
import com.br.zup.proposta.proposal.transaction.TransactionStatus;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckCardTask {
    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private CheckCardNumberClient checkCardNumberClient;

    @Scheduled(fixedDelayString = "${deplay.check.card.task}")
    private void execute() {
        // pegar lista de todos as propostas approvadas
        List<Proposal> proposals = proposalRepository.findAllByCardIsNullAndStatusEquals(TransactionStatus.ELIGIBLE);
        //fazer requisição para api
        if(proposals.size() > 0) {
            for (Proposal proposal : proposals) {
                try {
                    CardResponse cardResponse = checkCardNumberClient.checkCardProposal(new CardRequest(proposal.getDocument(), proposal.getName(), proposal.getId().toString()));
                    Card card = cardResponse.toModel(proposal);
                    cardRepository.save(card);
                    proposal.updateCardNumber(card);
                    proposalRepository.save(proposal);
                } catch (FeignException.UnprocessableEntity e) {
                    return;
                }
            }
        }

        // caso positivo atrelar número do cartão na proposta e salvar
    }
}
