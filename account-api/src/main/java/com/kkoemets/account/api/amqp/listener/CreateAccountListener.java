package com.kkoemets.account.api.amqp.listener;

import com.kkoemets.account.api.amqp.message.CreateAccountMessage;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateAccountListener extends AmqpListener<CreateAccountMessage> {
    private static final Logger log = getLogger(CreateAccountListener.class);

    @Override
    public void onMessage(CreateAccountMessage message) {
        log.info("{}", message);
    }

}
