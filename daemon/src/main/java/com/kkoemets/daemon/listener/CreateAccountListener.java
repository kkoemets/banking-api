package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.CreateAccountMessage;
import com.kkoemets.core.bl.account.CreateAccount;
import com.kkoemets.core.bl.account.CreateAccountDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateAccountListener extends AmqpListener<CreateAccountMessage> {
    private static final Logger log = getLogger(CreateAccountListener.class);

    @Autowired
    private CreateAccount createAccount;

    @Override
    public void onMessage(CreateAccountMessage message) {
        createAccount.create(new CreateAccountDto(message.getAccountId(), message.getCustomerId(),
                message.getCurrencies(), message.getCountryCode()));
    }

}
