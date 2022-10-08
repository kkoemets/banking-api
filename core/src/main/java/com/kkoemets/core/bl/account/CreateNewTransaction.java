package com.kkoemets.core.bl.account;

import com.kkoemets.core.exception.InvalidStateException;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.AddTransactionDto;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.kkoemets.core.exception.CoreExceptionMessage.ERROR_ACCOUNT_DOES_NOT_EXIST;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateNewTransaction {
    private static final Logger log = getLogger(CreateNewTransaction.class);

    @Autowired
    private AccountService accounts;
    @Autowired
    private TransactionService transactions;

    public void create(CreateNewTransactionDto dto) {
        TransactionId transactionId = dto.transactionId();
        log.info("Creating new transaction-{}", transactionId);
        AccountId accountId = dto.accountId();
        if (!accounts.exists(accountId)) {
            log.error("Account does not exist");
            throw new InvalidStateException(ERROR_ACCOUNT_DOES_NOT_EXIST);
        }

        if (transactions.exists(transactionId)) {
            log.warn("Transaction already exists");
            return;
        }

        log.info("Adding transaction");
        transactions
                .add(new AddTransactionDto(transactionId, accountId, dto.amount(), dto.description(), dto.direction()));

        log.info("Transaction created");
    }

}
