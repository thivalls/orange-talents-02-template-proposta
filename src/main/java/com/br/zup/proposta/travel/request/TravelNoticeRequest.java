package com.br.zup.proposta.travel.request;

import com.br.zup.proposta.card.model.Card;
import com.br.zup.proposta.travel.model.TravelNotice;
import io.micrometer.core.lang.NonNull;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class TravelNoticeRequest {
    @NotBlank
    private String travelTo;

    @NonNull
    @Future
    private LocalDateTime travelEndDate;

    public TravelNoticeRequest(@NotBlank String travelTo, @NonNull @Future LocalDateTime travelEndDate) {
        this.travelTo = travelTo;
        this.travelEndDate = travelEndDate;
    }

    public String getTravelTo() {
        return travelTo;
    }

    @NonNull
    public LocalDateTime getTravelEndDate() {
        return travelEndDate;
    }

    public TravelNotice toModel(Card card, String clientUserIp, String clientUserAgent) {
        return new TravelNotice(card, travelTo, travelEndDate, clientUserIp, clientUserAgent);
    }
}
