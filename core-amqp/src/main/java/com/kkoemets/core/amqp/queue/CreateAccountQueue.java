package com.kkoemets.core.amqp.queue;

import com.kkoemets.core.amqp.message.CreateAccountMessage;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountQueue extends AmqpQueue<CreateAccountMessage> {

    @Override
    String getQueueName() {
        return "create-account-queue";
    }

}
