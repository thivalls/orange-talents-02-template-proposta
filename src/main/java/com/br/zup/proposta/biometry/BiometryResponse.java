package com.br.zup.proposta.biometry;

import java.time.LocalDateTime;

public class BiometryResponse {
    private String biometryId;
    private LocalDateTime createdAt;
    private String biometry;

    public BiometryResponse(Biometry biometry) {
        this.biometryId = biometry.getId();
        this.createdAt = biometry.getCreatedAt();
        this.biometry = biometry.getBiometry();
    }

    public String getBiometryId() {
        return biometryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBiometry() {
        return biometry;
    }
}
