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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
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

    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
