package com.kkoemets.core.dao;

import com.kkoemets.core.mybatis.annotation.CoreDao;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CoreDao
public interface BalanceDao {

    Optional<Money> findBalance(@Param("accountId") AccountId accountId,
                                @Param("currency") Currency currency);

    void insertBalances(@Param("accountId") AccountId accountId,
                        @Param("amount") BigDecimal amount,
                        @Param("currencies") List<Currency> currencies);

    int increase(@Param("accountId") AccountId accountId,
                 @Param("byAmount") Money byAmount);

    int decrease(@Param("accountId") AccountId accountId,
                 @Param("byAmount") Money byAmount);

}
