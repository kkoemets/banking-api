package com.kkoemets.account.api.query.dao;

import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;

public class FindAccountQueryResultDto {
    private AccountId accountId;
    private CustomerId customerId;

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

}
