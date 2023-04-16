package com.markpetrov.test.exception;

import com.markpetrov.test.model.Currency;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long accountId, Currency currency) {
        super("Insufficient funds in account " + accountId + " for currency " + currency);
    }
}
