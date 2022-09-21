package com.kkoemets.account.api.requestconsumer;

import com.kkoemets.account.api.AccountApiRestTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;

public class CreateAccountJsonConsumerRestTest extends AccountApiRestTest {

    @Test
    public void successfullyConsume() {
        post("/api/v1/accounts",
                Map.of(
                        "customerId", 1L,
                        "countryCode", EE.getValue(),
                        "currencies", List.of("EUR", "SEK", "GBP", "USD")
                )
        );
    }

}
