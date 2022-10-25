package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.AccountApiRestTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateAccountJsonConsumerRestTest extends AccountApiRestTest {

    @Test
    public void successfullyConsume() {
        Map<String, Object> result = post("/api/v1/accounts",
                Map.of(
                        "customerId", 1L,
                        "countryCode", EE.getValue(),
                        "currencies", List.of("EUR", "SEK", "GBP", "USD")
                )
        );

        assertNotNull(result.get("balances"));
        assertNotNull(result.get("accountId"));
        assertNotNull(result.get("customerId"));
    }

}
