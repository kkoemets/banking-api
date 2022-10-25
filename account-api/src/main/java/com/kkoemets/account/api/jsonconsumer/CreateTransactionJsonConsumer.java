package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.bl.transaction.CreateNewTransaction;
import com.kkoemets.account.api.bl.transaction.CreateNewTransactionDto;
import com.kkoemets.account.api.bl.transaction.CreateNewTransactionResultDto;
import com.kkoemets.account.api.controller.json.request.CreateTransactionJson;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.StringUtils.trimWhitespace;

@Component
public class CreateTransactionJsonConsumer {

    @Autowired
    private CreateNewTransaction createNewTransaction;

    public ResponseEntity<Map<String, Object>> create(CreateTransactionJson json) {
        CreateNewTransactionDto dto = toDto(json);
        CreateNewTransactionResultDto resultDto = createNewTransaction.create(dto);

        Money transactionAmount = dto.amount();
        return ok(Map.of(
                "accountId", resultDto.accountId(),
                "transactionId", resultDto.transactionId(),
                "amount", transactionAmount.amount().toPlainString(),
                "currency", transactionAmount.currency(),
                "direction", dto.direction(),
                "description", dto.description(),
                "balance", resultDto.newBalance().amount().toPlainString()
        ));
    }

    private static CreateNewTransactionDto toDto(CreateTransactionJson json) {
        return new CreateNewTransactionDto(new AccountId(json.getAccountId()), createMoney(json),
                trimWhitespace(json.getDescription()),
                TransactionDirection.create(trimWhitespace(json.getDirection()))
        );
    }

    private static Money createMoney(CreateTransactionJson json) {
        return new Money(trimWhitespace(json.getAmount()),
                Currency.create(trimWhitespace(json.getCurrency())));
    }

}
