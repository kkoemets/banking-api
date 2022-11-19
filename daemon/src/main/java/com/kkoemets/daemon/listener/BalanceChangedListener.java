package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.BalanceChangedMessage;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class BalanceChangedListener extends AmqpListener<BalanceChangedMessage> {
    private static final Logger log = getLogger(BalanceChangedListener.class);

    @Override
    public void onMessage(BalanceChangedMessage message) {
        log.info("Received message-{}", message);
    }

}
