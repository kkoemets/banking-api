package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.AccountApiRestTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.kkoemets.account.api.utils.UrlUtil.replace;
import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.*;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FindTransactionsConsumerRestTest extends AccountApiRestTest {

    @Test
    public void successfullyFinds() {
        Map<String, Object> createAccountResult = post("/api/v1/accounts",
                Map.of(
                        "customerId", 1L,
                        "countryCode", EE.getValue(),
                        "currencies", List.of(EUR.getValue(), USD.getValue(), GBP.getValue(), SEK.getValue())
                )
        );

        String accountId = createAccountResult.get("accountId").toString();
        post(replace("/api/v1/accounts/{accountId}/transactions", accountId),
                Map.of(
                        "amount", "1.00",
                        "currency", EUR.getValue(),
                        "direction", IN.getCode(),
                        "description", "test description"
                )
        );

        assertNotNull(get(replace("/api/v1/accounts/{accountId}/transactions", accountId)));
    }

}
