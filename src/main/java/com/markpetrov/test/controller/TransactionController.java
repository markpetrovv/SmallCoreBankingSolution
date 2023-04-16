package com.markpetrov.test.controller;

import com.markpetrov.test.dto.CreateTransactionRequest;
import com.markpetrov.test.exception.AccountNotFoundException;
import com.markpetrov.test.model.Transaction;
import com.markpetrov.test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request.getAccountId(), request.getAmount(),
                request.getCurrency(), request.getDirection(), request.getDescription());
    }

    @GetMapping("/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
