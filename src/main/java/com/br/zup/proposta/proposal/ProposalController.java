package com.br.zup.proposta.proposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping
    @Transactional
    public ResponseEntity store(@RequestBody @Valid ProposalRequest request) {
        Proposal proposal = request.toModel();

        Optional<Proposal> proposalExist = proposalRepository.findByDocument(request.getDocument());
        if (proposalExist.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        proposalRepository.save(proposal);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
