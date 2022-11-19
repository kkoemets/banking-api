package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.TransactionCreatedMessage;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class TransactionCreatedListener extends AmqpListener<TransactionCreatedMessage> {
    private static final Logger log = getLogger(TransactionCreatedListener.class);

    @Override
    public void onMessage(TransactionCreatedMessage message) {
        log.info("Received message-{}", message);
    }

}
