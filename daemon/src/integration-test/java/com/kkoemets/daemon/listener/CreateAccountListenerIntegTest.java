package com.kkoemets.daemon.listener;

import com.kkoemets.core.amqp.message.CreateAccountMessage;
import com.kkoemets.daemon.DaemonIntegTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateAccountListenerIntegTest extends DaemonIntegTest {

    @Autowired
    private CreateAccountListener listener;

    @Test
    public void processMessage() {
        listener.onMessage(new CreateAccountMessage());
    }

}
