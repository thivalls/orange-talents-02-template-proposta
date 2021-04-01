package com.br.zup.proposta.proposal;

import javax.validation.constraints.NotBlank;

public class AddressRequest {
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String zipcode;

    public AddressRequest(@NotBlank String street, @NotBlank String number, @NotBlank String zipcode) {
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Address toAddress() {
        return new Address(street, number, zipcode);
    };
}
