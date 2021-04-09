package com.br.zup.proposta.travel.model;

import com.br.zup.proposta.card.model.Card;
import io.micrometer.core.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_notices")
public class TravelNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Card card;

    @NotBlank
    private String travelTo;

    @NotNull
    @Future
    private LocalDateTime travelEndDate;

    @NotNull
    private LocalDateTime travelStartDate;

    @NotBlank
    private String clientIp;

    @NotBlank
    private String clientUserAgent;

    @Deprecated
    public TravelNotice() {
    }

    public TravelNotice(@NotNull Card card, @NotBlank String travelTo, @NotNull @Future LocalDateTime travelEndDate, @NotBlank String clientIp, @NotBlank String clientUserAgent) {
        this.card = card;
        this.travelTo = travelTo;
        this.travelEndDate = travelEndDate;
        this.travelStartDate = LocalDateTime.now();
        this.clientIp = clientIp;
        this.clientUserAgent = clientUserAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelNotice)) return false;

        TravelNotice that = (TravelNotice) o;

        if (!card.equals(that.card)) return false;
        if (!travelTo.equals(that.travelTo)) return false;
        return travelEndDate.equals(that.travelEndDate);
    }

    @Override
    public int hashCode() {
        int result = card.hashCode();
        result = 31 * result + travelTo.hashCode();
        result = 31 * result + travelEndDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TravelNotice{" +
                "id=" + id +
                ", card=" + card +
                ", travelTo='" + travelTo + '\'' +
                ", travelEndDate=" + travelEndDate +
                ", travelStartDate=" + travelStartDate +
                ", clientIp='" + clientIp + '\'' +
                ", clientUserAgent='" + clientUserAgent + '\'' +
                '}';
    }
}
