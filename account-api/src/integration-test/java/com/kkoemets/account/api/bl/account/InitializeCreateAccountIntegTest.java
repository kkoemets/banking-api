package com.kkoemets.account.api.bl.account;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.amqp.queue.CreateAccountQueue;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kkoemets.account.api.exception.ExceptionMessage.ERROR_DISALLOWED_CURRENCIES;
import static com.kkoemets.account.api.exception.ExceptionMessage.ERROR_DUPLICATE_CURRENCIES;
import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InitializeCreateAccountIntegTest extends AccountApiIntegTest {

    @Autowired
    private InitializeCreateAccount initializeCreateAccount;
    @Autowired
    private CreateAccountQueue createAccountQueue;

    @AfterEach
    public void afterEach() {
        initializeCreateAccount.setCreateAccountQueue(createAccountQueue);
    }

    @Test
    public void successfullyInitialize() {
        InitializeCreateAccountResult result = initializeCreateAccount.initialize(createDto());
        assertNotNull(result.accountId());
        assertNotNull(result.customerId());
        assertNotNull(result.balances());
    }

    @Test
    public void failsIfDisallowedCurrency() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> initializeCreateAccount.initialize(createDto(List.of(EUR, Currency.create("RUB")))));

        assertThat(exception.getMessage(), is(ERROR_DISALLOWED_CURRENCIES));
    }

    @Test
    public void failsIfDuplicateCurrenciesAreInDto() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> initializeCreateAccount.initialize(createDto(List.of(EUR, USD, EUR))));

        assertThat(exception.getMessage(), is(ERROR_DUPLICATE_CURRENCIES));
    }

    @Test
    public void queueMessageIsSent() {
        CreateAccountQueue mockQueue = mock(CreateAccountQueue.class);
        initializeCreateAccount.setCreateAccountQueue(mockQueue);

        verify(mockQueue, times(0)).send(any());
        initializeCreateAccount.initialize(createDto());
        verify(mockQueue, times(1)).send(any());
    }

    private InitializeCreateAccountDto createDto() {
        return new InitializeCreateAccountDto(new CustomerId(1L), List.of(EUR, USD), EE);
    }

    private InitializeCreateAccountDto createDto(List<Currency> currencies) {
        return new InitializeCreateAccountDto(new CustomerId(1L), currencies, EE);
    }

}
