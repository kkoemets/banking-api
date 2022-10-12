package com.kkoemets.account.api.controller;

import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kkoemets.account.api.controller.ValidationMessage.FIELD_INVALID_VALUE;
import static com.kkoemets.account.api.controller.ValidationMessage.FIELD_MANDATORY;
import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;

public class CreateAccountTests extends AccountControllerIntegTest {
    @Test
    public void success() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCustomerId(1L);
        json.setCountryCode(EE.getValue());
        json.setCurrencies(List.of(EUR.getValue(), USD.getValue()));

        assertThatHttp200(() -> postCreateAccount(json));
    }

    @Test
    public void failsIfInvalidCurrencies_tooShort() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCurrencies(List.of(EUR.getValue(), "D"));

        assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidCurrencies_null() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCurrencies(null);

        assertFieldValidationError(json, "currencies", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidCurrencies_empty() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCurrencies(List.of());

        assertFieldValidationError(json, "currencies", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidCurrencies_tooLong() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCurrencies(List.of(EUR.getValue(), "DEEE"));

        assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidCurrencies_notAlphabetic() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCurrencies(List.of("EU7"));

        assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidCurrency_duplicates() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCurrencies(List.of(EE.getValue(), EE.getValue()));

        assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfIfInvalidCustomerId_missing() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCustomerId(null);

        assertFieldValidationError(json, "customerId", FIELD_MANDATORY);
    }

    @Test
    public void failsIfIfInvalidCustomerId_zero() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCustomerId(0L);

        assertFieldValidationError(json, "customerId", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfIfInvalidCustomerId_negative() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCustomerId(-1L);

        assertFieldValidationError(json, "customerId", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidCountry_null() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCountryCode(null);

        assertFieldValidationError(json, "countryCode", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidCountry_blank() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCountryCode("");

        assertFieldValidationError(json, "countryCode", FIELD_MANDATORY);
    }

    @Test
    public void failsIfInvalidCountry_tooShort() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCountryCode("E");

        assertFieldValidationError(json, "countryCode", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidCountry_tooLong() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCountryCode("EEE");

        assertFieldValidationError(json, "countryCode", FIELD_INVALID_VALUE);
    }

    @Test
    public void failsIfInvalidCountry_notAlphabetic() {
        CreateAccountJson json = new CreateAccountJson();
        json.setCountryCode("E3");

        assertFieldValidationError(json, "countryCode", FIELD_INVALID_VALUE);
    }

    private void assertFieldValidationError(CreateAccountJson json, String currencies, String message) {
        assertFieldValidationError(() -> postCreateAccount(json), currencies, message);
    }

}
