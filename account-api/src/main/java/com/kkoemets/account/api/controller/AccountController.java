package com.kkoemets.account.api.controller;

import com.kkoemets.account.api.controller.json.request.CreateAccountJson;
import com.kkoemets.account.api.jsonconsumer.CreateAccountJsonConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
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
    private Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return singletonMap("error", ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(e -> new SimpleEntry<>(((FieldError) e).getField(), e.getDefaultMessage()))
                .collect(groupingBy(SimpleEntry::getKey))
                .entrySet()
                .stream()
                .map(this::createFieldWithListOfErrors)
                .collect(toList())
        );
    }

    private SimpleEntry<String, List<String>> createFieldWithListOfErrors(
            Entry<String, List<SimpleEntry<String, String>>> e) {
        return new SimpleEntry<>(e.getKey(), e.getValue()
                .stream()
                .map(SimpleEntry::getValue)
                .collect(toList())
        );
    }

}
