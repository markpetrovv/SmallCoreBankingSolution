package com.markpetrov.test.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {
    private Long accountId;
    private Long transactionId;
    private BigDecimal amount;
    private Currency currency;
    private TransactionDirection direction;
    private String description;
    private BigDecimal balanceAfterTransaction;

    public Transaction() {

    }
    public Transaction(Long accountId, Long transactionId, BigDecimal amount, Currency currency, TransactionDirection direction, String description, BigDecimal balanceAfterTransaction) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.direction = direction;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
