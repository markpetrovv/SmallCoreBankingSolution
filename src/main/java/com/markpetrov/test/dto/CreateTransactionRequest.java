package com.markpetrov.test.dto;

import com.markpetrov.test.model.Currency;
import com.markpetrov.test.model.TransactionDirection;

import java.math.BigDecimal;

public class CreateTransactionRequest {
    private Long accountId;
    private BigDecimal amount;
    private Currency currency;
    private TransactionDirection direction;
    private String description;

    // Getters and setters

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransactionDirection getDirection() {
        return direction;
    }

    public String getDescription() {
        return description;
    }
}
