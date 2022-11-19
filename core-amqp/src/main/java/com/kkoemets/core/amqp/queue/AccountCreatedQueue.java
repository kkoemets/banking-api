package com.kkoemets.core.amqp.queue;

import com.kkoemets.core.amqp.message.AccountCreatedMessage;
import org.springframework.stereotype.Component;

@Component
public class AccountCreatedQueue extends AmqpQueue<AccountCreatedMessage> {

    @Override
    String getQueueName() {
        return "account-created";
    }

}
