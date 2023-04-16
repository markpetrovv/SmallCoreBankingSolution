package com.markpetrov.test.service;

import com.markpetrov.test.model.Account;
import com.markpetrov.test.model.Currency;

import java.util.List;

public interface AccountService {
    Account createAccount(Long customerId, String country, List<Currency> currencies);
    Account getAccount(Long accountId);
}
