package com.br.zup.proposta.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    public Optional<Card> findByCardNumber(String cardId);
}
