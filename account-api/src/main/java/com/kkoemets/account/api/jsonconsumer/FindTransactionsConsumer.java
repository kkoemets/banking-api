package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.exception.NotFoundException;
import com.kkoemets.account.api.query.TransactionQuery;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.domain.id.AccountId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@Component
public class FindTransactionsConsumer {
    private static final Logger log = getLogger(FindTransactionsConsumer.class);

    @Autowired
    private AccountService accounts;
    @Autowired
    private TransactionQuery transactionQuery;

    public ResponseEntity<Map<String, Object>> find(AccountId accountId) {
        log.info("Find account-{} transactions", accountId);

        if (!accounts.exists(accountId)) {
            log.warn("Account does not exist");
            throw new NotFoundException();
        }

        return ok(Map.of("transactions",
                transactionQuery.findTransactions(accountId)
                        .stream()
                        .map(transaction -> Map.of(
                                "accountId", transaction.getAccountId().getValue(),
                                "transactionId", transaction.getTransactionId().getValue(),
                                "amount", transaction.getAmount().amount().toPlainString(),
                                "currency", transaction.getAmount().currency().getValue(),
                                "direction", transaction.getDirection().getCode(),
                                "description", transaction.getDescription()
                        ))
                        .collect(toList())));
    }

}
