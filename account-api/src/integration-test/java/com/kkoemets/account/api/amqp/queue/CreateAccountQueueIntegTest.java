package com.kkoemets.account.api.amqp.queue;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.core.amqp.message.CreateAccountMessage;
import com.kkoemets.core.amqp.queue.CreateAccountQueue;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;

public class CreateAccountQueueIntegTest extends AccountApiIntegTest {

    @Autowired
    private AccountService accounts;
    @Autowired
    private CreateAccountQueue queue;

    @Test
    public void sendMessage() {
        queue.send(createMessage());
    }

    private CreateAccountMessage createMessage() {
        CreateAccountMessage message = new CreateAccountMessage();
        message.setAccountId(accounts.getNextSeqValue());
        message.setCustomerId(new CustomerId(1L));
        message.setCurrencies(List.of(EUR));
        message.setCountryCode(EE);
        return message;
    }

}
