package com.markpetrov.test.service;

import com.markpetrov.test.exception.AccountNotFoundException;
import com.markpetrov.test.mapper.AccountMapper;
import com.markpetrov.test.mapper.BalanceMapper;
import com.markpetrov.test.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private BalanceMapper balanceMapper;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private AccountServiceImpl accountService;

    // From the AccountServiceImpl
    @Test
    void testGetAccount_accountExists() {
        // Prepare test data
        Long accountId = 1L;
        Account account = new Account();
        account.setAccountId(accountId);
        account.setCustomerId(1L);
        account.setCountry("US");
        List<Balance> balances = new ArrayList<>();
        balances.add(new Balance(accountId, Currency.USD, BigDecimal.valueOf(1000)));
        account.setBalances(balances);

        // Configure mocks
        when(accountMapper.findById(accountId)).thenReturn(account);
        when(balanceMapper.findByAccountId(accountId)).thenReturn(balances);

        // Call the method being tested
        Account result = accountService.getAccount(accountId);

        // Verify the results
        assertNotNull(result);
        assertEquals(account.getAccountId(), result.getAccountId());
        assertEquals(account.getCustomerId(), result.getCustomerId());
        assertEquals(account.getCountry(), result.getCountry());
        assertEquals(account.getBalances().size(), result.getBalances().size());
    }
    @Test
    void testGetAccount_accountDoesNotExist() {
        // Prepare test data
        Long invalidCustomerId = null;

        // Call the method being tested and expect an exception
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(invalidCustomerId));
    }
    @Test
    void testCreateAccount_validCountry() {
        // Prepare test data
        Long customerId = 1L;
        String validCountry = "US";
        List<Currency> currencies = Arrays.asList(Currency.USD, Currency.EUR);

        // Configure mocks
        doNothing().when(accountMapper).insert(any(Account.class));
        doNothing().when(balanceMapper).insert(any(Balance.class));
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(Balance.class));

        // Call the method being tested
        Account result = accountService.createAccount(customerId, validCountry, currencies);

        // Verify the results
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(validCountry, result.getCountry());
        assertEquals(2, result.getBalances().size());
    }
    @Test
    void testCreateAccount_nullCountry() {
        // Prepare test data
        Long customerId = 1L;
        String invalidCountry = null;
        List<Currency> currencies = Arrays.asList(Currency.USD, Currency.EUR);

        // Call the method being tested and expect an exception
        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(customerId, invalidCountry, currencies));
    }
    @Test
    void testCreateAccount_validCurrency() {
        // Prepare test data
        Long customerId = 1L;
        String country = "EST";
        List<Currency> validCurrencies = Arrays.asList(Currency.SEK);

        // Configure mocks
        doNothing().when(accountMapper).insert(any(Account.class));
        doNothing().when(balanceMapper).insert(any(Balance.class));

        // Call the method being tested
        Account result = accountService.createAccount(customerId, country, validCurrencies);

        // Verify the results
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(country, result.getCountry());
        assertEquals(1, result.getBalances().size());
        assertEquals(validCurrencies.get(0), result.getBalances().get(0).getCurrency());
        assertEquals(BigDecimal.ZERO, result.getBalances().get(0).getAvailableAmount());
    }
    @Test
    void testGetAccount_WithMultipleBalances_Positive() {
        // Prepare test data
        Long accountId = 1L;
        Account account = new Account();
        account.setAccountId(accountId);
        account.setCustomerId(1L);
        account.setCountry("US");
        List<Balance> balances = Arrays.asList(
                new Balance(accountId, Currency.USD, BigDecimal.valueOf(1000)),
                new Balance(accountId, Currency.EUR, BigDecimal.valueOf(500))
        );
        account.setBalances(balances);

        // Configure mocks
        when(accountMapper.findById(accountId)).thenReturn(account);
        when(balanceMapper.findByAccountId(accountId)).thenReturn(balances);

        // Call the method being tested
        Account result = accountService.getAccount(accountId);

        // Verify the results
        assertNotNull(result);
        assertEquals(account, result);
        assertEquals(2, result.getBalances().size());
        assertEquals(BigDecimal.valueOf(1000), result.getBalances().get(0).getAvailableAmount());
        assertEquals(Currency.USD, result.getBalances().get(0).getCurrency());
        assertEquals(BigDecimal.valueOf(500), result.getBalances().get(1).getAvailableAmount());
        assertEquals(Currency.EUR, result.getBalances().get(1).getCurrency());
    }
    @Test
    void testGetAccount_WithMultipleBalances_Negative() {
        // Prepare test data
        Long invalidAccountId = 999L;

        // Configure mocks
        when(accountMapper.findById(invalidAccountId)).thenReturn(null);

        // Call the method being tested and expect an exception
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(invalidAccountId));
    }
    @Test
    void testGetAccount_WithNoBalances() {
        // Prepare test data
        Long accountId = 1L;
        Account account = new Account();
        account.setAccountId(accountId);
        account.setCustomerId(1L);
        account.setCountry("US");
        account.setBalances(null);

        // Configure mocks
        when(accountMapper.findById(accountId)).thenReturn(account);
        when(balanceMapper.findByAccountId(accountId)).thenReturn(Collections.emptyList());

        // Call the method being tested
        Account result = accountService.getAccount(accountId);

        // Verify the results
        assertNotNull(result);
        assertEquals(account.getAccountId(), result.getAccountId());
        assertEquals(account.getCustomerId(), result.getCustomerId());
        assertEquals(account.getCountry(), result.getCountry());
        assertEquals(0, result.getBalances().size());
    }

}
