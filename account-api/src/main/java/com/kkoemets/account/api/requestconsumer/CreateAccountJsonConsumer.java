package com.kkoemets.account.api.requestconsumer;

import com.kkoemets.account.api.controller.CreateAccountJson;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateAccountJsonConsumer {
    private static final Logger log = getLogger(CreateAccountJsonConsumer.class);

    public ResponseEntity<?> create(CreateAccountJson json) {
        return null;
    }

}
