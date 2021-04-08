package com.br.zup.proposta.http.client.feign;

import com.br.zup.proposta.card.response.BlockCardResponse;
import com.br.zup.proposta.card.request.CardBlockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "blockCard", url = "http://localhost:8888/")
public interface BlockCardClient {
    @PostMapping("/api/cartoes/{id}/bloqueios")
    public BlockCardResponse externalBlockCardService(@PathVariable String id, CardBlockRequest cardBlockRequest);
}

