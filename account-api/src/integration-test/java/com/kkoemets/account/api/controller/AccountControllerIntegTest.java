package com.kkoemets.account.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoemets.account.api.AccountApi;
import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static com.kkoemets.account.api.controller.ValidationMessage.FIELD_INVALID_VALUE;
import static com.kkoemets.account.api.controller.ValidationMessage.FIELD_MANDATORY;
import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@SpringBootTest(classes = AccountApi.class, webEnvironment = RANDOM_PORT)
public class AccountControllerIntegTest extends AccountApiIntegTest {

    @Autowired
    private ObjectMapper om;
    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Nested
    class CreateAccountTests {

        @Test
        public void success() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCustomerId(1L);
            json.setCountryCode(EE.getValue());
            json.setCurrencies(List.of(EUR.getValue(), USD.getValue()));

            assertThat(postCreateAccount(json).getStatusCode()).is(new Condition<>(s -> s == OK, "HTTP 200"));
        }

        @Test
        public void failsIfInvalidCurrencies_tooShort() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCurrencies(List.of(EUR.getValue(), "D"));

            assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfInvalidCurrencies_null() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCurrencies(null);

            assertFieldValidationError(json, "currencies", FIELD_MANDATORY);
        }

        @Test
        public void failsIfInvalidCurrencies_empty() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCurrencies(List.of());

            assertFieldValidationError(json, "currencies", FIELD_MANDATORY);
        }

        @Test
        public void failsIfInvalidCurrencies_tooLong() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCurrencies(List.of(EUR.getValue(), "DEEE"));

            assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfInvalidCurrencies_notAlphabetic() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCurrencies(List.of("EU7"));

            assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfInvalidCurrency_duplicates() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCurrencies(List.of(EE.getValue(), EE.getValue()));

            assertFieldValidationError(json, "currencies", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfIfInvalidCustomerId_missing() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCustomerId(null);

            assertFieldValidationError(json, "customerId", FIELD_MANDATORY);
        }

        @Test
        public void failsIfIfInvalidCustomerId_zero() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCustomerId(0L);

            assertFieldValidationError(json, "customerId", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfIfInvalidCustomerId_negative() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCustomerId(-1L);

            assertFieldValidationError(json, "customerId", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfInvalidCountry_null() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCountryCode(null);

            assertFieldValidationError(json, "countryCode", FIELD_MANDATORY);
        }

        @Test
        public void failsIfInvalidCountry_blank() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCountryCode("");

            assertFieldValidationError(json, "countryCode", FIELD_MANDATORY);
        }

        @Test
        public void failsIfInvalidCountry_tooShort() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCountryCode("E");

            assertFieldValidationError(json, "countryCode", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfInvalidCountry_tooLong() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCountryCode("EEE");

            assertFieldValidationError(json, "countryCode", FIELD_INVALID_VALUE);
        }

        @Test
        public void failsIfInvalidCountry_notAlphabetic() throws Exception {
            CreateAccountJson json = new CreateAccountJson();
            json.setCountryCode("E3");

            assertFieldValidationError(json, "countryCode", FIELD_INVALID_VALUE);
        }

    }

    private void assertFieldValidationError(CreateAccountJson json, String currencies, String fieldMandatory)
            throws JsonProcessingException {
        assertThat(findFieldValidationErrors(postCreateAccount(json), currencies))
                .contains(fieldMandatory);
    }

    private List<String> findFieldValidationErrors(ResponseEntity<String> response, String fieldName)
            throws JsonProcessingException {
        return getErrorMap(response)
                .stream()
                .filter(e -> nonNull(e.get(fieldName)))
                .findFirst()
                .orElseThrow()
                .get(fieldName);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, List<String>>> getErrorMap(ResponseEntity<String> response)
            throws JsonProcessingException {
        return (List<Map<String, List<String>>>) extractBody(response).get("error");
    }

    private Map<String, Object> extractBody(ResponseEntity<String> response) throws JsonProcessingException {
        return om.readValue(response.getBody(), new TypeReference<>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }

    private ResponseEntity<String> postCreateAccount(CreateAccountJson json) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        return restTemplate.exchange(
                "http://localhost:%d%s".formatted(port, "/api/v1/accounts"),
                HttpMethod.POST, new HttpEntity<>(om.writeValueAsString(json), headers), String.class);
    }

}
