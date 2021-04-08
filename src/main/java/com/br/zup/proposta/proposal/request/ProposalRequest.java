package com.br.zup.proposta.proposal.request;

import com.br.zup.proposta.proposal.Proposal;
import com.br.zup.proposta.shared.validation.CpfOrCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProposalRequest {

    @NotBlank
    @CpfOrCnpj(message = "Invalid document")
    private String document;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotNull
    private AddressRequest addressRequest;

    @Positive
    @NotNull
    private BigDecimal salary;

    public ProposalRequest(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank AddressRequest addressRequest, @Positive @NotNull BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.addressRequest = addressRequest;
        this.salary = salary;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public AddressRequest getAddressRequest() {
        return addressRequest;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Proposal toModel() {
        return new Proposal(document, email, name, addressRequest.toAddress(), salary);
    }
}
