package com.br.zup.proposta.http.client.feign;

import com.br.zup.proposta.card.response.CardResponse;
import com.br.zup.proposta.card.request.CardRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "card", url = "http://localhost:8888/")
public interface CheckCardNumberClient {
    @PostMapping("/api/cartoes")
    public CardResponse checkCardProposal(CardRequest cardRequest);
}
