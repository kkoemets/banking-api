package com.kkoemets.account.api.bl.account;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InitializeCreateAccountIntegTest extends AccountApiIntegTest {

    @Autowired
    private InitializeCreateAccount initializeCreateAccount;

    @Test
    public void successfullyInitialize() {
        InitializeCreateAccountResult result = initializeCreateAccount
                .initialize(new InitializeCreateAccountDto(new CustomerId(1L), List.of(EUR, USD), EE));
        assertNotNull(result.accountId());
        assertNotNull(result.customerId());
        assertNotNull(result.balances());
    }

}
