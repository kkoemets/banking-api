package com.kkoemets.core.amqp.queue;

import com.kkoemets.core.amqp.message.BalanceChangedMessage;
import org.springframework.stereotype.Component;

@Component
public class BalanceChangedQueue extends AmqpQueue<BalanceChangedMessage> {

    @Override
    String getQueueName() {
        return "balance-changed";
    }

}
