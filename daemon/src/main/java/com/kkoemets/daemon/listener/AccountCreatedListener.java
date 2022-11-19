package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.AccountCreatedMessage;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class AccountCreatedListener extends AmqpListener<AccountCreatedMessage> {
    private static final Logger log = getLogger(AccountCreatedListener.class);

    @Override
    public void onMessage(AccountCreatedMessage message) {
        log.info("Received message-{}", message);
    }

}
