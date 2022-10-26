package com.kkoemets.account.api.jsonconsumer;

import com.kkoemets.account.api.exception.NotFoundException;
import com.kkoemets.account.api.query.AccountQuery;
import com.kkoemets.account.api.query.BalanceQuery;
import com.kkoemets.account.api.query.dao.FindAccountQueryResultDto;
import com.kkoemets.domain.id.AccountId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;

@Component
public class FindAccountConsumer {
    private static final Logger log = getLogger(FindAccountConsumer.class);

    @Autowired
    private AccountQuery accountQuery;
    @Autowired
    private BalanceQuery balanceQuery;

    public ResponseEntity<Map<String, Object>> find(AccountId accountId) {
        log.info("Finding data for account-{}", accountId);

        Optional<FindAccountQueryResultDto> foundData = accountQuery.find(accountId);
        if (foundData.isEmpty()) {
            log.warn("Account not found");
            throw new NotFoundException();
        }

        FindAccountQueryResultDto data = foundData.get();
        return ok(Map.of(
                "accountId", data.getAccountId(),
                "customerId", data.getCustomerId(),
                "balances", balanceQuery.findBalances(accountId)
                        .stream()
                        .map(balance -> Map.of("amount", balance.getAmount().amount().toPlainString(),
                                "currency", balance.getAmount().currency()))
                        .collect(toList())
        ));
    }

}
