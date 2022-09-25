package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.AccountApiIntegTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateAccountJsonConsumerIntegTest extends AccountApiIntegTest {

    @Autowired
    private CreateAccountJsonConsumer jsonConsumer;

    @Test
    public void success() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCurrency_tooShort() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCurrency_tooLong() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCurrency_notAlphabetic() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCurrency_duplicates() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfIfInvalidCustomerId_missing() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCountry_tooShort() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCountry_tooLong() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCountry_notAlphabetic() {
        throw new UnsupportedOperationException();
    }

}
