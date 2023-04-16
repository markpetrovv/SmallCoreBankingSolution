package com.markpetrov.test.mapper;

import com.markpetrov.test.model.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    void insert(Account account);
    Account findById(Long accountId);
}
