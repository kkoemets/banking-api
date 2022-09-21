package com.kkoemets.account.api.bl.account;

import com.kkoemets.core.service.AccountService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class InitializeCreateAccount {
    private static final Logger log = getLogger(InitializeCreateAccount.class);

    @Autowired
    private AccountService accounts;

    public InitializeCreateAccountResult initialize(InitializeCreateAccountDto dto) {
        CustomerId customerId = dto.customerId();
        List<Currency> currencies = dto.currencies();
        CountryIsoCode2 country = dto.country();
        log.info("Creating account for customer-{} with currencies-{}, country-{}", customerId, currencies, country);

        return new InitializeCreateAccountResult(accounts.getNextSeqValue(), customerId,
                currencies
                        .stream()
                        .map(Money::zero)
                        .collect(toList()));
    }

}
