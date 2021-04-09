package com.br.zup.proposta.biometry;

import com.br.zup.proposta.biometry.model.Biometry;
import com.br.zup.proposta.biometry.request.BiometryRequest;
import com.br.zup.proposta.biometry.response.BiometryResponse;
import com.br.zup.proposta.card.model.Card;
import com.br.zup.proposta.card.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("proposals/biometry")
public class BiometryController {
    @Autowired
    CardRepository cardRepository;

    @PersistenceContext
    private EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(BiometryController.class);

    @PostMapping("{cardId}")
    @Transactional
    public ResponseEntity<Void> store(@PathVariable String cardId, @RequestBody BiometryRequest request) {
        logger.info("Verificando existência de cartão");
        Optional<Card> card = cardRepository.findById(cardId);
        if(card.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Verificando se numero do cartão confere com o registro no banco");
        if(!card.get().getCardNumber().equals(request.getCardId())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        logger.info("Verificando biometria");
        Biometry biometry = request.toModel(card.get());
        em.persist(biometry);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(biometry.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{cardId}/{biometryId}")
    public ResponseEntity<BiometryResponse> show(@PathVariable(name = "cardId") String cardId, @PathVariable(name = "biometryId") String biometryId) {
        logger.info("Verificando existência de cartão");
        Optional<Card> card = cardRepository.findById(cardId);
        if(card.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Biometry biometry = em.find(Biometry.class, biometryId);
        if(biometry == null) {
            return ResponseEntity.notFound().build();
        }

        BiometryResponse biometryResponse = new BiometryResponse(biometry);
        return ResponseEntity.ok(biometryResponse);
    }
}
