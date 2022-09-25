package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.CreateAccountMessage;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.daemon.DaemonIntegTest;
import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;

public class CreateAccountListenerIntegTest extends DaemonIntegTest {

    @Autowired
    private AccountService accounts;
    @Autowired
    private CreateAccountListener listener;

    @Test
    public void processMessage() {
        listener.onMessage(createMessage());
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
