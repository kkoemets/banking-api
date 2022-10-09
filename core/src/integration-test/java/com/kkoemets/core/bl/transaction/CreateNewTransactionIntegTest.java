package com.kkoemets.core.bl.transaction;

import com.kkoemets.core.CoreIntegTest;
import com.kkoemets.core.exception.InvalidStateException;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kkoemets.core.exception.CoreExceptionMessage.ERROR_ACCOUNT_DOES_NOT_EXIST;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class CreateNewTransactionIntegTest extends CoreIntegTest {

    @Autowired
    private CreateNewTransaction createNewTransaction;
    @Autowired
    private TransactionService transactions;
    @Autowired
    private AccountService accounts;

    @Test
    public void transactionIsCreated() {
        CreateNewTransactionDto dto = createDto();

        TransactionId transactionId = dto.transactionId();
        assertFalse(transactions.exists(transactionId));

        createNewTransaction.create(dto);

        assertTrue(transactions.exists(transactionId));
    }

    @Test
    public void correctAmount() {
        CreateNewTransactionDto dto = createDto();

        createNewTransaction.create(dto);

        Money amount = transactions.findAmount(dto.transactionId());
        Money expectedMoney = dto.amount();

        assertEquals(0, amount.amount().compareTo(expectedMoney.amount()));
        assertThat(amount.currency(), is(expectedMoney.currency()));
    }

    @Test
    public void correctDirection() {
        CreateNewTransactionDto dto = createDto();

        createNewTransaction.create(dto);

        assertThat(transactions.findDirection(dto.transactionId()), is(dto.direction()));
    }

    @Test
    public void correctDescription() {
        CreateNewTransactionDto dto = createDto();

        createNewTransaction.create(dto);

        assertThat(transactions.findDescription(dto.transactionId()), is(dto.description()));
    }

    @Test
    public void failsIfAccountDoesNotExist() {
        AccountId accountId = new AccountId(100000001L);
        assertFalse(accounts.exists(accountId));
        InvalidStateException exception = assertThrows(InvalidStateException.class,
                () -> createNewTransaction.create(createDto(accountId)));

        assertThat(exception.getMessage(), is(ERROR_ACCOUNT_DOES_NOT_EXIST));
    }

    @Test
    public void transactionIsInNewStatus() {
        CreateNewTransactionDto dto = createDto();

        createNewTransaction.create(dto);

        assertTrue(transactions.isInNewStatus(dto.transactionId()));
    }

    @Test
    public void successIfAlready() {
        CreateNewTransactionDto dto = createDto();

        TransactionId transactionId = dto.transactionId();
        assertFalse(transactions.exists(transactionId));

        createNewTransaction.create(dto);
        assertTrue(transactions.exists(transactionId));
        createNewTransaction.create(dto);
    }

    private CreateNewTransactionDto createDto() {
        return createDto(createAccount());
    }

    private CreateNewTransactionDto createDto(AccountId accountId) {
        return new CreateNewTransactionDto(transactions.getNextSeqValue(),
                accountId, new Money(TEN, EUR), "test_description", IN);
    }

}
