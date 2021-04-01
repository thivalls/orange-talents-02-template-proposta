package com.br.zup.proposta.proposal;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String number;
    private String zipcode;

    @Deprecated
    public Address() {
    }

    public Address(String street, String number, String zipcode) {
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

//    public Address toAddress() {
//        return new Address(street, number, zipcode);
//    }
}
