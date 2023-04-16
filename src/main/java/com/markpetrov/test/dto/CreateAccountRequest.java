package com.markpetrov.test.dto;

import com.markpetrov.test.model.Currency;

import java.util.List;

public class CreateAccountRequest {
    private Long customerId;
    private String country;
    private List<Currency> currencies;


    // Getters and setters
    public Long getCustomerId() {
        return customerId;
    }

    public String getCountry() {
        return country;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

}
