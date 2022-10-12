package com.kkoemets.account.api.bl.transaction;

import com.kkoemets.account.api.AccountApiIntegTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InitializeCreateNewTransactionIntegTest extends AccountApiIntegTest {

    @Autowired
    private InitializeCreateNewTransaction initializeCreateNewTransaction;

    @Test
    public void createsQueueMessageToCreateNewTransaction() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void transactionIdIsCreated() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfNoBalanceInTransactionCurrency() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInsufficientFunds() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void transactionIdIsNotStored() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfAccountDoesNotExist() {
        throw new UnsupportedOperationException();
    }

}
