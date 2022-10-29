package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.AccountApiRestTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.kkoemets.account.api.utils.UrlUtil.replace;
import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.*;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateTransactionJsonConsumerRestTest extends AccountApiRestTest {

    @Test
    public void successfullyConsume() {
        Map<String, Object> createAccountResult = post("/api/v1/accounts",
                Map.of(
                        "customerId", 1L,
                        "countryCode", EE.getValue(),
                        "currencies", List.of(EUR.getValue(), USD.getValue(), GBP.getValue(), SEK.getValue())
                )
        );

        String currency = EUR.getValue();
        String direction = IN.getCode();
        String amount = "1.00";
        String accountId = createAccountResult.get("accountId").toString();
        String description = "test description";
        Map<String, Object> result = post(replace("/api/v1/accounts/{accountId}/transactions", accountId),
                Map.of(
                        "amount", amount,
                        "currency", currency,
                        "direction", direction,
                        "description", description
                )
        );

        assertThat(result.get("accountId").toString(), is(accountId));
        assertNotNull(result.get("transactionId"));
        assertThat(result.get("amount"), is(amount));
        assertThat(result.get("currency"), is(currency));
        assertThat(result.get("direction"), is(direction));
        assertThat(result.get("description"), is(description));
        assertNotNull(result.get("balance"));
    }

}
