package com.markpetrov.test.service;

import com.markpetrov.test.model.Currency;
import com.markpetrov.test.model.TransactionDirection;
import com.markpetrov.test.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Long accountId, BigDecimal amount, Currency currency, TransactionDirection direction, String description);

    List<Transaction> getTransactionsByAccountId(Long accountId);

}
