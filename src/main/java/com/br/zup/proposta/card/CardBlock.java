package com.br.zup.proposta.card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "card_blocks")
public class CardBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime blockedAt;
    private String clientIp;
    private String userAgent;

    @OneToOne
    @NotNull
    private Card card;

    @Deprecated
    public CardBlock() {
    }

    public CardBlock(@NotBlank String clientIp, @NotBlank String userAgent, @NotNull Card card) {
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.blockedAt = LocalDateTime.now();
        this.card = card;
    }
}
