package com.br.zup.proposta.proposal;

import com.br.zup.proposta.transaction.TransactionStatus;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private String email;

    @NotBlank
    private String name;

    @Embedded
    private Address address;

    @Positive
    @NotNull
    private BigDecimal salary;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Deprecated
    public Proposal() {
    }

    public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, Address address, @Positive @NotNull BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = TransactionStatus.NOT_ELIGIBLE;
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

    public Address getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", salary=" + salary +
                '}';
    }

    public void updateStatus(TransactionStatus status) {
        // testar
        if(status == null) throw new IllegalArgumentException("Status can not be null");

        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
