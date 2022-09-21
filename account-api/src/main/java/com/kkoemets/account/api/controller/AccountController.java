package com.kkoemets.account.api.controller;

import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import com.kkoemets.account.api.requestconsumer.CreateAccountJsonConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private CreateAccountJsonConsumer createAccount;

    @PostMapping("/api/v1/accounts")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountJson json) {
        return createAccount.create(json);
    }
}
