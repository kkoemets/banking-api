package com.kkoemets.account.api.amqp.message;

import com.kkoemets.domain.id.AccountId;

public class CreateAccountMessage implements AmqpMessage {
    private AccountId accountId;

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

}
