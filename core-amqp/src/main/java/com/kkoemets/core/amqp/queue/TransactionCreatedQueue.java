package com.kkoemets.core.amqp.queue;

import com.kkoemets.core.amqp.message.TransactionCreatedMessage;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreatedQueue extends AmqpQueue<TransactionCreatedMessage> {

    @Override
    String getQueueName() {
        return "transaction-created";
    }

}
