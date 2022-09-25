package com.kkoemets.account.api.amqp.queue;

import com.kkoemets.account.api.amqp.message.AmqpMessage;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AmqpQueue<C extends AmqpMessage> {
    private static final Logger log = getLogger(AmqpQueue.class);

    @Autowired
    private RabbitTemplate template;

    public void send(C message) {
        template.convertAndSend(getQueueName(), message);
        log.info("Sent message to queue-{}", getQueueName());
    }

    abstract String getQueueName();

}
