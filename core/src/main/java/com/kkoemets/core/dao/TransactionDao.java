package com.kkoemets.core.dao;

import com.kkoemets.core.mybatis.annotation.CoreDao;
import com.kkoemets.core.service.AddTransactionDto;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.TransactionId;
import org.apache.ibatis.annotations.Param;

@CoreDao
public interface TransactionDao {

    TransactionId add(@Param("dto") AddTransactionDto dto);

    boolean exists(@Param("transactionId") TransactionId transactionId);

    Money findAmount(@Param("transactionId") TransactionId transactionId);

    TransactionDirection findDirection(@Param("transactionId") TransactionId transactionId);

    String findDescription(@Param("transactionId") TransactionId transactionId);

}
