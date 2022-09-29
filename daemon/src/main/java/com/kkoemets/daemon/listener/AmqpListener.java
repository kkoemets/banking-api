package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.AmqpMessage;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AmqpListener<C extends AmqpMessage> implements MessageListener {
    private static final Logger log = getLogger(AmqpListener.class);

    @Autowired
    private MessageConverter converter;

    public abstract void onMessage(C message);

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        log.info("{} received message-{}",
                message.getMessageProperties().getConsumerQueue(), new String(message.getBody()));
        onMessage((C) converter.fromMessage(message));
    }

}
