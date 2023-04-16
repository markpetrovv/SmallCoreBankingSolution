package com.markpetrov.test.mapper;

import com.markpetrov.test.model.Balance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BalanceMapper {
    void insert(Balance balance);
    List<Balance> findByAccountId(Long accountId);
    void update(Balance balance);
}
