package com.markpetrov.test.mapper;

import com.markpetrov.test.model.Transaction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {
    void insert(Transaction transaction);
    List<Transaction> findByAccountId(Long accountId);
}
