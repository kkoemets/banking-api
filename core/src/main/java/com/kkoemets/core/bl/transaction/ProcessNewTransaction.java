package com.kkoemets.core.bl.transaction;

import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionStatus;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.kkoemets.domain.codes.TransactionStatus.PROCESSED;
import static com.kkoemets.domain.codes.TransactionStatus.REJECTED;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ProcessNewTransaction {
    private static final Logger log = getLogger(ProcessNewTransaction.class);

    @Autowired
    private TransactionService transactions;
    @Autowired
    private AccountService accounts;
    @Autowired
    private BalanceService balances;

    @Transactional
    public void process(TransactionId transactionId) {
        if (!transactions.isInNewStatus(transactionId)) {
            log.warn("Transaction status is not new");
            return;
        }

        AccountId accountId = transactions.findAccountId(transactionId);
        accounts.lock(accountId);

        transactions.setStatus(transactionId, processTransaction(transactionId, accountId));
    }

    private TransactionStatus processTransaction(TransactionId transactionId, AccountId accountId) {
        return processTransaction(transactionId, accountId, transactions.findAmount(transactionId));
    }

    private TransactionStatus processTransaction(TransactionId transactionId, AccountId accountId,
                                                 Money transactionsAmount) {
        return balances
                .findBalance(accountId, transactionsAmount.currency())
                .map(balance -> transactions.isOutgoing(transactionId)
                        ? processOutgoing(accountId, balance, transactionsAmount)
                        : processIncoming(accountId, transactionsAmount))
                .orElse(REJECTED);
    }

    private TransactionStatus processOutgoing(AccountId accountId, Money accountBalance, Money transactionAmount) {
        if (accountBalance.lessThan(transactionAmount)) {
            return REJECTED;
        }

        balances.decrease(accountId, transactionAmount);
        return PROCESSED;
    }

    private TransactionStatus processIncoming(AccountId accountId, Money transactionAmount) {
        balances.increase(accountId, transactionAmount);
        return PROCESSED;
    }

}
