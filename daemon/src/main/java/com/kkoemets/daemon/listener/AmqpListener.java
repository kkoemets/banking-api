package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.AmqpMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AmqpListener<C extends AmqpMessage> implements MessageListener {

    @Autowired
    private MessageConverter converter;

    public abstract void onMessage(C message);

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        onMessage((C) converter.fromMessage(message));
    }

}
