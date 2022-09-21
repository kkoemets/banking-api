package com.kkoemets.account.api.requestconsumer;

import com.kkoemets.account.api.bl.account.InitializeCreateAccount;
import com.kkoemets.account.api.bl.account.InitializeCreateAccountDto;
import com.kkoemets.account.api.bl.account.InitializeCreateAccountResult;
import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.CustomerId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@Component
public class CreateAccountJsonConsumer {
    private static final Logger log = getLogger(CreateAccountJsonConsumer.class);

    @Autowired
    private InitializeCreateAccount initializeCreateAccount;

    public ResponseEntity<?> create(CreateAccountJson json) {
        log.info("Received request to initialize a new account");
        return ok(toResponse(initializeCreateAccount.initialize(toDto(json))));
    }

    private Map<String, Object> toResponse(InitializeCreateAccountResult result) {
        return Map.of(
                "accountId", result.accountId().getValue(),
                "customerId", result.customerId().getValue(),
                "balances", result.balances()
                        .stream()
                        .map(balance -> Map.of(
                                "amount", balance.amount(),
                                "currency", balance.currency().getValue()))
                        .collect(toList())
        );
    }

    private InitializeCreateAccountDto toDto(CreateAccountJson json) {
        return new InitializeCreateAccountDto(
                new CustomerId(json.getCustomerId()),
                json.getCurrencies()
                        .stream()
                        .map(Currency::create)
                        .collect(toList()),
                CountryIsoCode2.create(json.getCountryCode()));
    }

}
