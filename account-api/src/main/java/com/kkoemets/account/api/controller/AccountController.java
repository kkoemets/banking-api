package com.kkoemets.account.api.controller;

import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import com.kkoemets.account.api.jsonconsumer.CreateAccountJsonConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class AccountController {

    @Autowired
    private CreateAccountJsonConsumer createAccount;

    @PostMapping("/api/v1/accounts")
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreateAccountJson json) {
        return createAccount.create(json);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Collections.singletonMap("errors", ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(toMap(e -> ((FieldError) e).getField(), DefaultMessageSourceResolvable::getDefaultMessage)));
    }

}
