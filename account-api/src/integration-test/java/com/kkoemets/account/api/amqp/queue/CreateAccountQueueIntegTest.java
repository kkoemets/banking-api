package com.kkoemets.account.api.amqp.queue;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.amqp.message.CreateAccountMessage;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateAccountQueueIntegTest extends AccountApiIntegTest {

    @Autowired
    private CreateAccountQueue queue;

    @Test
    public void sendMessage() {
        CreateAccountMessage message = new CreateAccountMessage();
        message.setAccountId(new AccountId(1L));

        queue.send(message);
    }

}
