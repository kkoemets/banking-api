package com.kkoemets.account.api.bl.transaction;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class InitializeCreateNewTransaction {
    private static final Logger log = getLogger(InitializeCreateNewTransaction.class);

    public InitializeCreateNewTransactionResult initialize(InitializeCreateNewTransactionDto dto) {
        log.info("Initializing new transaction, account-{}", dto.accountId());
        return new InitializeCreateNewTransactionResult();
    }

}
