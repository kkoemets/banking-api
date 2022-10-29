package com.kkoemets.account.api.query.dao;

import com.kkoemets.account.api.mybatis.annotation.AccountApiQueryDao;
import com.kkoemets.domain.id.AccountId;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@AccountApiQueryDao
public interface TransactionQueryDao {

    List<FindTransactionQueryResultDto> findTransactions(@Param("accountId") AccountId accountId);

}
