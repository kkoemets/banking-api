package com.kkoemets.account.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FindAccountTests extends AccountControllerIntegTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void successfullyFindAccount() {
        AccountId accountId = createAccount();
        assertThatHttp200(() -> get("/api/v1/accounts/{accountId}"
                .replace("{accountId}", accountId.getValue().toString())));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void correctResponseData() throws Exception {
        AccountId accountId = createAccount();
        ResponseEntity<String> response = get("/api/v1/accounts/{accountId}"
                .replace("{accountId}", accountId.getValue().toString()));
        Map<String, Object> json = objectMapper.readValue(response.getBody(), Map.class);
        assertNotNull(json.get("customerId"));
        assertThat(json.get("accountId").toString(), is(accountId.getValue().toString()));
        assertNotNull(json.get("balances"));
    }

    @Test
    public void failsIfAccountDoesNotExist() {
        assertThatHttp404(() -> get("/api/v1/accounts/9999999"));
    }

}
