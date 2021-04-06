package com.br.zup.proposta.biometry;

import com.br.zup.proposta.card.Card;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "biometries")
public class Biometry {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @ManyToOne
    private Card card;

    @NotBlank
    private String biometry;

    @NotNull
    private LocalDateTime createdAt;

    @Deprecated
    public Biometry() {
    }

    public Biometry(@NotNull Card card, @NotBlank String biometry) {
        this.card = card;
        this.biometry = biometry;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBiometry() {
        return biometry;
    }
}
