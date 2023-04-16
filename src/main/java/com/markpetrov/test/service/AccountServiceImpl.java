package com.markpetrov.test.service;

import com.markpetrov.test.mapper.AccountMapper;
import com.markpetrov.test.exception.AccountNotFoundException;
import com.markpetrov.test.mapper.BalanceMapper;
import com.markpetrov.test.model.Account;
import com.markpetrov.test.model.Balance;
import com.markpetrov.test.model.Currency;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BalanceMapper balanceMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public Account createAccount(Long customerId, String country, List<Currency> currencies) {
        if (country == null) {
            throw new IllegalArgumentException("Invalid Country");
        }
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setCountry(country);
        accountMapper.insert(account);

        List<Balance> balances = new ArrayList<>();
        for (Currency currency : currencies) {
            Balance balance = new Balance();
            balance.setAccountId(account.getAccountId());
            balance.setAvailableAmount(BigDecimal.ZERO);
            balance.setCurrency(currency);
            balanceMapper.insert(balance);
            balances.add(balance);

            // Publish balance creation event to RabbitMQ
            rabbitTemplate.convertAndSend("balanceExchange", "balance.create", balance);
        }


        account.setBalances(balances);
        return account;
    }

    @Override
    public Account getAccount(Long accountId) {
        Account account = accountMapper.findById(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        List<Balance> balances = balanceMapper.findByAccountId(accountId);
        account.setBalances(balances);
        return account;
    }
}
