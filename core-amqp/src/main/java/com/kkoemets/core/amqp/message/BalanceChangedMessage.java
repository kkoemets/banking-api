package com.kkoemets.core.amqp.message;

import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;

import java.math.BigDecimal;

public class BalanceChangedMessage implements AmqpMessage {
    private AccountId accountId;
    private BigDecimal initialBalance;
    private BigDecimal balance;
    private Currency currency;

    public BalanceChangedMessage() {
    }

    public BalanceChangedMessage(AccountId accountId, BigDecimal initialBalance, BigDecimal balance, Currency currency) {
        this.accountId = accountId;
        this.initialBalance = initialBalance;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "BalanceChangedMessage{" +
                "accountId=" + accountId +
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }

}
