package com.kkoemets.account.api.bl.account;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.core.amqp.queue.CreateAccountQueue;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class InitializeCreateAccountIntegTest extends AccountApiIntegTest {

    @Autowired
    private InitializeCreateAccount initializeCreateAccount;
    @Autowired
    private CreateAccountQueue createAccountQueue;
    @Autowired
    private ApplicationContext applicationContext;

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
        throw new UnsupportedOperationException();
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

}
