package com.br.zup.proposta.card;

import com.br.zup.proposta.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    public Optional<Card> findByCardNumber(String cardId);
}
