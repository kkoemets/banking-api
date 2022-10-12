package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.bl.transaction.InitializeCreateNewTransaction;
import com.kkoemets.account.api.bl.transaction.InitializeCreateNewTransactionDto;
import com.kkoemets.account.api.controller.json.request.CreateTransactionJson;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyMap;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.StringUtils.trimWhitespace;

@Component
public class CreateTransactionJsonConsumer {

    @Autowired
    private InitializeCreateNewTransaction initializeCreateNewTransaction;

    public ResponseEntity<?> create(CreateTransactionJson json) {
        initializeCreateNewTransaction.initialize(toDto(json));
        return ok(emptyMap());
    }

    private static InitializeCreateNewTransactionDto toDto(CreateTransactionJson json) {
        return new InitializeCreateNewTransactionDto(new AccountId(json.getAccountId()), createMoney(json),
                TransactionDirection.create(trimWhitespace(json.getDirection())),
                trimWhitespace(json.getDescription())
        );
    }

    private static Money createMoney(CreateTransactionJson json) {
        return new Money(trimWhitespace(json.getAmount()),
                Currency.create(trimWhitespace(json.getCurrency())));
    }

}
