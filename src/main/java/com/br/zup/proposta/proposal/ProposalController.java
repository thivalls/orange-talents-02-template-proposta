package com.br.zup.proposta.proposal;

import com.br.zup.proposta.http.client.feign.ProposalFinancialAnalysisClient;
import com.br.zup.proposta.proposal.request.ProposalRequest;
import com.br.zup.proposta.proposal.response.ProposalStatusResponse;
import com.br.zup.proposta.proposal.transaction.TransactionRequest;
import com.br.zup.proposta.proposal.transaction.TransactionResponse;
import com.br.zup.proposta.proposal.transaction.TransactionStatus;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("proposals")
public class ProposalController {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalFinancialAnalysisClient proposalFinancialAnalysisClient;

    @PostMapping
    @Transactional
    public ResponseEntity store(@RequestBody @Valid ProposalRequest request) {

        Optional<Proposal> proposalExist = proposalRepository.findByDocument(request.getDocument());
        if (proposalExist.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Proposal proposal = request.toModel();
        proposalRepository.save(proposal);

        TransactionStatus status = submitForExternalAnalysis(proposal);

        proposal.updateStatus(status);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    public TransactionStatus submitForExternalAnalysis(Proposal proposal) {
        try {
            TransactionRequest transactionRequest = new TransactionRequest(proposal);
            TransactionResponse apiAnalysisFinancialResult = proposalFinancialAnalysisClient.checkClientFinancialStatus(transactionRequest);
            return apiAnalysisFinancialResult.resolveEnum();
        } catch (FeignException.UnprocessableEntity e) {
            return TransactionStatus.NOT_ELIGIBLE;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error"); // return exception to force rollback in database
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity show(@PathVariable Long id) {
        Optional<Proposal> proposal = proposalRepository.findById(id);

        if(!proposal.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ProposalStatusResponse proposalStatusResponse = new ProposalStatusResponse(proposal.get());

        return ResponseEntity.ok(proposalStatusResponse);
    }
}
