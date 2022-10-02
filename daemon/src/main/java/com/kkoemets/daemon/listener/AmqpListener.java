package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.AmqpMessage;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AmqpListener<C extends AmqpMessage> implements MessageListener {
    private static final Logger log = getLogger(AmqpListener.class);
    private static final int REQUEST_ID_LENGTH = 7;

    @Autowired
    private MessageConverter converter;

    public abstract void onMessage(C message);

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        MDC.put("requestId", randomUUID().toString().substring(0, REQUEST_ID_LENGTH));

        try {
            MessageProperties messageProperties = message.getMessageProperties();

            log.info("{} received message-{} correlationId-{}", messageProperties.getConsumerQueue(),
                    new String(message.getBody()), messageProperties.getCorrelationId());

            onMessage((C) converter.fromMessage(message));
        } finally {
            MDC.clear();
        }
    }

}
