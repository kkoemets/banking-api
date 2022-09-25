package com.kkoemets.core.amqp.message;

import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;

import java.util.List;

public class CreateAccountMessage implements AmqpMessage {
    private AccountId accountId;
    private CustomerId customerId;
    private List<Currency> currencies;
    private CountryIsoCode2 countryCode;

    public CreateAccountMessage(AccountId accountId, CustomerId customerId, List<Currency> currencies,
                                CountryIsoCode2 countryCode) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.currencies = currencies;
        this.countryCode = countryCode;
    }

    public CreateAccountMessage() {
    }

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

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public CountryIsoCode2 getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryIsoCode2 countryCode) {
        this.countryCode = countryCode;
    }

}
