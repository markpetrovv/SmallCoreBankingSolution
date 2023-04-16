package com.markpetrov.test.service;

import com.markpetrov.test.exception.AccountNotFoundException;
import com.markpetrov.test.exception.InsufficientFundsException;
import com.markpetrov.test.exception.InvalidCurrencyException;
import com.markpetrov.test.exception.InvalidTransactionDirectionException;
import com.markpetrov.test.mapper.AccountMapper;
import com.markpetrov.test.mapper.BalanceMapper;
import com.markpetrov.test.mapper.TransactionMapper;
import com.markpetrov.test.model.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BalanceMapper balanceMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public Transaction createTransaction(Long accountId, BigDecimal amount, Currency currency,
                                         TransactionDirection direction, String description) {
        Account account = accountService.getAccount(accountId);

        Balance balance = account.getBalances().stream()
                .filter(b -> b.getCurrency() == currency)
                .findFirst()
                .orElseThrow(() -> new InvalidCurrencyException("Invalid Currency"));

        BigDecimal newBalance;
        if (direction == TransactionDirection.IN) {
            newBalance = balance.getAvailableAmount().add(amount);
        } else if (direction == TransactionDirection.OUT) {
            if (balance.getAvailableAmount().compareTo(amount) < 0) {
                throw new InsufficientFundsException(accountId, currency);
            }
            newBalance = balance.getAvailableAmount().subtract(amount);
        } else {
            throw new InvalidTransactionDirectionException(direction);
        }

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setDirection(direction);
        transaction.setDescription(description);
        transaction.setBalanceAfterTransaction(newBalance);
        transactionMapper.insert(transaction);

        balance.setAvailableAmount(newBalance);
        balanceMapper.update(balance);

        // Publish transaction event to RabbitMQ
        rabbitTemplate.convertAndSend("transactionExchange", "transaction.create", transaction);

        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        Account account = accountMapper.findById(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        return transactionMapper.findByAccountId(accountId);
    }
}
