package com.br.zup.proposta.http.client.feign;

import com.br.zup.proposta.card.CardResponse;
import com.br.zup.proposta.card.CardRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "card", url = "http://localhost:8888/")
public interface CheckCardNumberClient {
    @PostMapping("/api/cartoes")
    public CardResponse checkCardProposal(CardRequest cardRequest);
}
