package com.br.zup.proposta.travel;

import com.br.zup.proposta.card.CardRepository;
import com.br.zup.proposta.card.model.Card;
import com.br.zup.proposta.travel.model.TravelNotice;
import com.br.zup.proposta.travel.request.TravelNoticeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("cards/travel/notice")
public class TravelNoticeController {
    @Autowired
    private CardRepository cardRepository;

    @PersistenceContext
    private EntityManager em;

    @PostMapping("{cardId}")
    @Transactional
    public ResponseEntity<Void> store(@PathVariable String cardId, @RequestBody @Valid TravelNoticeRequest request) {

        Optional<Card> card = cardRepository.findById(cardId);

        if (card.isEmpty()) return ResponseEntity.notFound().build();
        Assert.notNull(card.get(), "The card must not be bull");

        String clientUserIp = "192.168.1.2";
        String clientUserAgent = "Mozilla/5.0 (<system-information>) <platform> (<platform-details>) <extensions>";
        TravelNotice travelNotice = request.toModel(card.get(), clientUserIp, clientUserAgent);

        TypedQuery<TravelNotice> query = em.createQuery("select r from TravelNotice r where r.card.id = :card and r.travelTo = :to and MONTH(r.travelEndDate) = :end", TravelNotice.class);
        query.setParameter("card", cardId);
        query.setParameter("to", request.getTravelTo());
        query.setParameter("end", request.getTravelEndDate().getMonthValue());

        if (query.getResultList().size() > 0) return ResponseEntity.badRequest().build();
        Assert.state(query.getResultList().size() <= 1, "THIS IS A BUG - The notice already been registered.");

        em.persist(travelNotice);

        return ResponseEntity.ok().build();
    }
}
