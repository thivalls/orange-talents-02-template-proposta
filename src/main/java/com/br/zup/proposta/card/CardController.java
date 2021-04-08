package com.br.zup.proposta.card;

import com.br.zup.proposta.biometry.BiometryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("cards")
public class CardController {

    @Autowired
    CardRepository cardRepository;

    @PersistenceContext
    private EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(CardController.class);

    @PostMapping("/{cardId}/block")
    @Transactional
    public ResponseEntity<?> store(@PathVariable Long cardId, @RequestBody  @Valid CardBlockRequest request) {

        logger.info("CHECKING IF CARD EXIST");
        Optional<Card> card = cardRepository.findById(cardId);
        if(!card.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // verificar se o cartao pertence ao usuario que está requisitando

        if(card.get().getStatus().equals(CardStatus.BLOCKED)) return ResponseEntity.unprocessableEntity().build();

        card.get().blockCard();

        cardRepository.save(card.get());

        String userIp = "192.168.1.2";
        String userAgent = "Mozilla/5.0 (<system-information>) <platform> (<platform-details>) <extensions>";
        CardBlock cardBlock = request.toModel(userIp, userAgent, card.get());
        em.persist(cardBlock);

        // return 200 if block process has been concluded
        return ResponseEntity.ok().build();
    }
}
