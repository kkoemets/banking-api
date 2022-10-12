package com.kkoemets.account.api.controller;

import com.kkoemets.account.api.controller.json.request.CreateTransactionJson;
import com.kkoemets.domain.codes.TransactionDirection;
import org.junit.jupiter.api.Test;

import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static com.kkoemets.domain.codes.TransactionDirection.OUT;


public class CreateTransactionTests extends AccountControllerIntegTest {

    @Test
    public void successfullyCreateOutgoingTransaction() {
        assertThatHttp200(() -> postCreateTransaction(createJson(OUT)));
    }

    @Test
    public void successfullyCreateIncomingTransaction() {
        assertThatHttp200(() -> postCreateTransaction(createJson(IN)));
    }

    @Test
    public void failsIfInvalidDirection_unknownValue() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidAmount_notNumeric() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidAmount_negative() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidAmount_tooManyDecimals() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCurrency_missing() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidCurrency_invalidFormat() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidDescription_missing() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void failsIfInvalidDescription_tooLong() {
        throw new UnsupportedOperationException();
    }

    private void assertFieldValidationError(CreateTransactionJson json, String currencies, String message) {
        assertFieldValidationError(() -> postCreateTransaction(json), currencies, message);
    }

    private CreateTransactionJson createJson(TransactionDirection direction) {
        CreateTransactionJson json = new CreateTransactionJson();
        json.setAccountId(createAccount().getValue());
        json.setAmount("1.00");
        json.setCurrency(EUR.getValue());
        json.setDirection(direction.getCode());
        json.setDescription("test_description");
        return json;
    }

}
