package com.markpetrov.test.exception;

import com.markpetrov.test.model.Currency;

public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
