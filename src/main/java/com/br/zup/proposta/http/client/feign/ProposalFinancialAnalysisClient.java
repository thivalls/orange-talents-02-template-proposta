package com.br.zup.proposta.http.client.feign;

import com.br.zup.proposta.transaction.TransactionRequest;
import com.br.zup.proposta.transaction.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "transaction", url = "http://localhost:9999")
public interface ProposalFinancialAnalysisClient {
    @PostMapping("/api/solicitacao")
    public TransactionResponse checkClientFinancialStatus(TransactionRequest transactionRequest);
}
