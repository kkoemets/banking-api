package com.kkoemets.core.amqp.message;

import com.kkoemets.domain.id.AccountId;

public class AccountCreatedMessage implements AmqpMessage {
    private AccountId accountId;

    public AccountCreatedMessage() {
    }

    public AccountCreatedMessage(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AccountCreatedMessage{" +
                "accountId=" + accountId +
                '}';
    }

}
