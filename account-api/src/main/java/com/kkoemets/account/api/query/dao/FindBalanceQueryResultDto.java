package com.kkoemets.account.api.query.dao;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;

public class FindBalanceQueryResultDto {
    private AccountId accountId;
    private Money amount;

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

}
