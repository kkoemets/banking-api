package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.AccountApiRestTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FindAccountConsumerRestTest extends AccountApiRestTest {

    @Test
    public void successfullyFind() {
        Map<String, Object> accountCreation = post("/api/v1/accounts",
                Map.of(
                        "customerId", 1L,
                        "countryCode", EE.getValue(),
                        "currencies", Stream.of(EUR, SEK, GBP, USD)
                                .map(code -> code.getValue())
                                .collect(Collectors.toList())
                )
        );

        Map<String, Object> foundData = get("/api/v1/accounts/{accountId}"
                .replace("{accountId}", accountCreation.get("accountId").toString()));

        assertNotNull(foundData.get("accountId"));
        assertNotNull(foundData.get("customerId"));
        assertNotNull(foundData.get("balances"));
    }

}
