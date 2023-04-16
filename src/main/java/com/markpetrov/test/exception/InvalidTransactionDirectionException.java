package com.markpetrov.test.exception;

import com.markpetrov.test.model.TransactionDirection;

public class InvalidTransactionDirectionException extends RuntimeException {
    public InvalidTransactionDirectionException(TransactionDirection direction) {
        super("Invalid transaction direction: " + direction);
    }
}
