package com.kkoemets.account.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoemets.account.api.controller.json.request.CreateTransactionJson;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static com.kkoemets.account.api.utils.UrlUtil.replace;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static com.kkoemets.domain.codes.TransactionDirection.OUT;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindTransactionsTest extends AccountControllerIntegTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void successfullyFindTransactions() {
        AccountId accountId = createAccount();
        assertThatHttp200(() -> get(replace("/api/v1/accounts/{accountId}/transactions",
                accountId.getValue().toString())));
    }

    @Test
    public void successIfNoTransactions() {
        AccountId accountId = createAccount();
        assertThatHttp200(() -> get(replace("/api/v1/accounts/{accountId}/transactions",
                accountId.getValue().toString())));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void correctResponseData() throws Exception {
        AccountId accountId = createAccount();
        Currency currency = EUR;
        createTransaction(accountId, createJson(IN, new Money(TEN, currency)));
        createTransaction(accountId, createJson(OUT, new Money(ONE, currency)));

        ResponseEntity<String> response = get(replace("/api/v1/accounts/{accountId}/transactions",
                accountId.getValue().toString()));
        List<Map<String, Object>> transactions = (List<Map<String, Object>>) objectMapper
                .readValue(response.getBody(), Map.class)
                .get("transactions");

        assertThat(transactions.size(), is(2));
        assertTrue(transactions.stream().anyMatch(tx -> tx.get("amount").equals("10.00")));
        assertTrue(transactions.stream().anyMatch(tx -> tx.get("amount").equals("1.00")));
        assertTrue(transactions.stream().anyMatch(tx -> tx.get("direction").equals(IN.getCode())));
        assertTrue(transactions.stream().anyMatch(tx -> tx.get("direction").equals(OUT.getCode())));
        assertTrue(transactions.stream().allMatch(tx -> tx.get("description").equals("test_description")));
        assertTrue(transactions.stream().allMatch(tx -> tx.get("currency").equals(currency.getValue())));
        assertTrue(transactions.stream().allMatch(tx -> tx.get("accountId").toString()
                .equals(accountId.getValue().toString())));
    }

    @Test
    public void failsIfAccountDoesNotExist() {
        assertThatHttp404(() -> get(replace("/api/v1/accounts/{accountId}/transactions", "999999")));
    }

    private CreateTransactionJson createJson(TransactionDirection direction, Money amount) {
        CreateTransactionJson json = new CreateTransactionJson();
        json.setAmount(amount.amount().toPlainString());
        json.setCurrency(amount.currency().getValue());
        json.setDirection(direction.getCode());
        json.setDescription("test_description");
        return json;
    }

}
