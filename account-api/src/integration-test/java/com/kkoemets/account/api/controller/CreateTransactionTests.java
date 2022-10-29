package com.kkoemets.account.api.controller;

import com.kkoemets.account.api.bl.balance.IncreaseBalance;
import com.kkoemets.account.api.controller.json.request.CreateTransactionJson;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static com.kkoemets.account.api.controller.ValidationMessage.*;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static com.kkoemets.domain.codes.TransactionDirection.OUT;
import static java.util.stream.Collectors.joining;


public class CreateTransactionTests extends AccountControllerIntegTest {

    @Autowired
    private IncreaseBalance increaseBalance;

    @Test
    public void successfullyCreateOutgoingTransaction() {
        AccountId accountId = createAccount();
        CreateTransactionJson json = createJson(OUT);
        increaseBalance.increase(accountId, new Money(BigDecimal.TEN, Currency.create(json.getCurrency())));
        assertThatHttp200(() -> createTransaction(accountId, json));
    }

    @Test
    public void successfullyCreateIncomingTransaction() {
        assertThatHttp200(() -> createTransaction(createAccount(), createJson(IN)));
    }

    @Test
    public void failsIfInvalidDirection_null() {
        CreateTransactionJson json = createJson(IN);
        json.setDirection(null);

        assertFieldValidationError(json, "direction", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidDirection_blank() {
        CreateTransactionJson json = createJson(IN);
        json.setDirection("");

        assertFieldValidationError(json, "direction", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidDirection_unknownValue() {
        CreateTransactionJson json = createJson(IN);
        json.setDirection("A");

        assertFieldValidationError(json, "direction", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidAmount_null() {
        CreateTransactionJson json = createJson(IN);
        json.setAmount(null);

        assertFieldValidationError(json, "amount", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidAmount_blank() {
        CreateTransactionJson json = createJson(IN);
        json.setAmount("");

        assertFieldValidationError(json, "amount", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidAmount_notNumeric() {
        CreateTransactionJson json = createJson(IN);
        json.setAmount("1.0A");

        assertFieldValidationError(json, "amount", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidAmount_negative() {
        CreateTransactionJson json = createJson(IN);
        json.setAmount("-0.01");

        assertFieldValidationError(json, "amount", FIELD_MUST_BE_MORE_THAN_ZERO);
    }

    @Test
    public void failsIfInvalidAmount_tooManyDecimals() {
        CreateTransactionJson json = createJson(IN);
        json.setAmount("-0.001");

        assertFieldValidationError(json, "amount", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidAmount_zeroAmount() {
        CreateTransactionJson json = createJson(IN);
        json.setAmount("0.00");

        assertFieldValidationError(json, "amount", FIELD_MUST_BE_MORE_THAN_ZERO);
    }

    @Test
    public void failsIfInvalidCurrency_null() {
        CreateTransactionJson json = createJson(IN);
        json.setCurrency(null);

        assertFieldValidationError(json, "currency", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidCurrency_blank() {
        CreateTransactionJson json = createJson(IN);
        json.setCurrency("");

        assertFieldValidationError(json, "currency", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidCurrency_invalidFormat() {
        CreateTransactionJson json = createJson(IN);
        json.setCurrency("EU");

        assertFieldValidationError(json, "currency", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidDescription_null() {
        CreateTransactionJson json = createJson(IN);
        json.setDescription(null);

        assertFieldValidationError(json, "description", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidDescription_blank() {
        CreateTransactionJson json = createJson(IN);
        json.setDescription("");

        assertFieldValidationError(json, "description", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidDescription_tooLong() {
        CreateTransactionJson json = createJson(IN);
        json.setDescription(
                IntStream
                        .rangeClosed(1, 141)
                        .mapToObj(i -> "a")
                        .collect(joining()));

        assertFieldValidationError(json, "description", FIELD_INVALID_TOO_LONG);
    }

    private void assertFieldValidationError(CreateTransactionJson json, String currencies,
                                            String message) {
        assertFieldValidationError(() -> createTransaction(createAccount(), json), currencies, message);
    }

    private CreateTransactionJson createJson(TransactionDirection direction) {
        CreateTransactionJson json = new CreateTransactionJson();
        json.setAmount("1.00");
        json.setCurrency(EUR.getValue());
        json.setDirection(direction.getCode());
        json.setDescription("test_description");
        return json;
    }

}
