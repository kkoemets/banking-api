package com.kkoemets.core.service;

import com.kkoemets.core.dao.BalanceDao;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

@Service
public class BalanceService {
    private static final BigDecimal DEFAULT_AMOUNT = ZERO;

    @Autowired
    private BalanceDao dao;

    public Optional<Money> findBalance(AccountId accountId, Currency currency) {
        return dao.findBalance(accountId, currency);
    }

    public List<Money> insert(AccountId accountId, List<Currency> currencies) {
        return dao.insertBalances(accountId, DEFAULT_AMOUNT, currencies);
    }

    public Money increase(AccountId accountId, Money byAmount) {
        return dao.increase(accountId, byAmount);
    }

    public Money decrease(AccountId accountId, Money byAmount) {
        return dao.decrease(accountId, byAmount);
    }

}
