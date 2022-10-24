package com.kkoemets.account.api.bl.transaction;

import com.kkoemets.account.api.bl.balance.DecreaseBalance;
import com.kkoemets.account.api.bl.balance.IncreaseBalance;
import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.AddTransactionDto;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_ACCOUNT_DOES_NOT_EXIST;
import static com.kkoemets.domain.codes.TransactionDirection.OUT;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateNewTransaction {
    private static final Logger log = getLogger(CreateNewTransaction.class);

    @Autowired
    private AccountService accounts;
    @Autowired
    private TransactionService transactions;
    @Autowired
    private IncreaseBalance increaseBalance;
    @Autowired
    private DecreaseBalance decreaseBalance;

    @Transactional
    public CreateNewTransactionResultDto create(CreateNewTransactionDto dto) {
        AccountId accountId = dto.accountId();
        log.info("Creating new transaction for account-{}", accountId);
        if (!accounts.exists(accountId)) {
            log.error("Account does not exist");
            throw new BadRequestException(ERROR_ACCOUNT_DOES_NOT_EXIST);
        }

        accounts.lock(accountId);

        Money transactionAmount = dto.amount();
        TransactionDirection direction = dto.direction();
        Money newBalance = direction == OUT
                ? decreaseBalance.decrease(accountId, transactionAmount)
                : increaseBalance.increase(accountId, transactionAmount);

        return new CreateNewTransactionResultDto(
                transactions.add(
                        new AddTransactionDto(accountId, transactionAmount, dto.description(), direction)),
                accountId, newBalance);
    }

}
