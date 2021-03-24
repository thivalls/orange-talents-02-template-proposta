package com.br.zup.proposta.proposal;

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

    @NotBlank
    private String address;

    @Positive
    @NotNull
    private BigDecimal salary;

    public ProposalRequest(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank String address, @Positive @NotNull BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ProposalRequest{" +
                "document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Proposal toModel() {
        return new Proposal(document, email, name, address, salary);
    }
}
