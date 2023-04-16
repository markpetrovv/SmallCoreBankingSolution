package com.markpetrov.test.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Balance implements Serializable {
    private Long accountId;
    private BigDecimal availableAmount;
    private Currency currency;

    public Balance() {
    }

    public Balance(Long accountId, Currency currency, BigDecimal availableAmount) {
        this.accountId = accountId;
        this.currency = currency;
        this.availableAmount = availableAmount;
    }
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
