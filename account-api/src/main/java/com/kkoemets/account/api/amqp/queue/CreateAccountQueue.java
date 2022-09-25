package com.kkoemets.account.api.amqp.queue;

import com.kkoemets.account.api.amqp.message.CreateAccountMessage;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountQueue extends AmqpQueue<CreateAccountMessage> {
    @Override
    String getQueueName() {
        return "account-queue";
    }

}
