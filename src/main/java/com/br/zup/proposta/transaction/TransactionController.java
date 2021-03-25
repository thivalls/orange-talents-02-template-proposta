package com.br.zup.proposta.transaction;

import com.br.zup.proposta.http.client.feign.ProposalFinancialAnalysisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private ProposalFinancialAnalysisClient proposalFinancialAnalysisClient;

    @PostMapping
    public String store(@RequestBody @Valid TransactionRequest request) {

        TransactionResponse apiAnalysisFinancialResult = proposalFinancialAnalysisClient.checkClientFinancialStatus(request);

        // request.updateStatus(apiAnalysisFinancialResult);
        return apiAnalysisFinancialResult.toString();
        // se receber COM_RESTRICAO configurar NOT_ELIGIBLE
        // se receber SEM_RESTRICAO configurar ELIGIBLE

        // salvar transação

        // return apiResult;
    }
}
