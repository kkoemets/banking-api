package com.kkoemets.core.bl.transaction;

import com.kkoemets.core.CoreIntegTest;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kkoemets.domain.codes.Currency.EUR;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessNewTransactionIntegTest extends CoreIntegTest {

    @Autowired
    private ProcessNewTransaction processNewTransaction;
    @Autowired
    private BalanceService balances;
    @Autowired
    private TransactionService transactions;

    @Test
    public void transactionIsInProcessedStatusIfEnoughAccountBalance() {
        TransactionId transactionId = createNewIncomingTransaction();
        assertTrue(transactions.isInNewStatus(transactionId));

        processNewTransaction.process(transactionId);

        assertTrue(transactions.isInProcessedStatus(transactionId));
    }

    @Test
    public void transactionIsInRejectedStatusIfNotEnoughAccountBalance() {
        TransactionId transactionId = createNewOutgoingTransaction();
        assertTrue(transactions.isInNewStatus(transactionId));

        processNewTransaction.process(transactionId);

        assertTrue(transactions.isInRejectedStatus(transactionId));
    }

    @Test
    public void transactionIsInRejectedStatusIfAccountBalanceForTransactionCurrencyDoesNotExist() {
        TransactionId transactionId = createNewIncomingTransaction();
        assertTrue(transactions.isInNewStatus(transactionId));

        AccountId accountId = transactions.findAccountId(transactionId);

        runQuery("UPDATE core.balance SET ccy = 'USD' WHERE acco_id = " + accountId.getValue());
        processNewTransaction.process(transactionId);

        assertTrue(transactions.isInRejectedStatus(transactionId));
    }

    @Test
    public void accountBalanceIsIncreasedIfIncomingTransaction() {
        TransactionId transactionId = createNewIncomingTransaction();
        assertTrue(transactions.isInNewStatus(transactionId));

        AccountId accountId = transactions.findAccountId(transactionId);
        Money balanceBefore = balances.findBalance(accountId, EUR).orElseThrow();

        processNewTransaction.process(transactionId);

        assertEquals(balanceBefore.plus(transactions.findAmount(transactionId)),
                balances.findBalance(accountId, EUR).orElseThrow());
    }

    @Test
    public void accountBalanceIsDecreasedIfOutgoingTransaction() {
        TransactionId transactionId = createNewOutgoingTransaction();
        assertTrue(transactions.isInNewStatus(transactionId));

        AccountId accountId = transactions.findAccountId(transactionId);
        balances.increase(accountId, new Money(TEN, EUR));
        Money balanceBefore = balances.findBalance(accountId, EUR).orElseThrow();

        processNewTransaction.process(transactionId);

        assertEquals(balances.findBalance(accountId, EUR).orElseThrow(),
                balanceBefore.minus(transactions.findAmount(transactionId)));
    }

}
