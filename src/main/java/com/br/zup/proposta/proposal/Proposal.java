package com.br.zup.proposta.proposal;

import com.br.zup.proposta.proposal.shared.validation.CpfOrCnpj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "proposals")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String document;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Positive
    @NotNull
    private BigDecimal salary;

    public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank String address, @Positive @NotNull BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Long getId() {
        return id;
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
}
