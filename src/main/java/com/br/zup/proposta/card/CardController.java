package com.br.zup.proposta.card;

import com.br.zup.proposta.card.model.Card;
import com.br.zup.proposta.card.model.CardBlock;
import com.br.zup.proposta.card.request.CardBlockRequest;
import com.br.zup.proposta.card.response.BlockCardResponse;
import com.br.zup.proposta.http.client.feign.BlockCardClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("cards")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BlockCardClient blockCardClient;

    @PersistenceContext
    private EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(CardController.class);

    @PostMapping("/{cardId}/block")
    @Transactional
    public ResponseEntity<?> store(@PathVariable String cardId, @RequestBody @Valid CardBlockRequest request) {

        Optional<Card> card = cardRepository.findByCardNumber(cardId);
        if(!card.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // verificar se o cartao pertence ao usuario que está requisitando

        if(card.get().getStatus().equals(CardStatus.BLOCKED)) return ResponseEntity.unprocessableEntity().build();

        try {

            BlockCardResponse blockCardResponse = blockCardClient.externalBlockCardService(cardId, request);
            String userIp = "192.168.1.2";
            String userAgent = "Mozilla/5.0 (<system-information>) <platform> (<platform-details>) <extensions>";
            if(blockCardResponse.getResponseStatus().equals("BLOQUEADO")) {
                CardBlock cardBlock = request.toModel(userIp, userAgent, card.get());
                em.persist(cardBlock);
                card.get().blockCard();
                cardRepository.save(card.get());
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
