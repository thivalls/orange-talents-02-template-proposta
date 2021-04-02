package com.br.zup.proposta.proposal;

import com.br.zup.proposta.proposal.transaction.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    Optional<Proposal> findByDocument(String document);

    List<Proposal> findAllByCardIsNullAndStatusEquals(TransactionStatus transactionStatus);
}
