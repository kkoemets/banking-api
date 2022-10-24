package com.kkoemets.account.api.bl.transaction;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.core.service.TransactionService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.*;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static com.kkoemets.domain.codes.TransactionDirection.IN;
import static com.kkoemets.domain.codes.TransactionDirection.OUT;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class CreateNewTransactionIntegTest extends AccountApiIntegTest {

    @Autowired
    private CreateNewTransaction createNewTransaction;
    @Autowired
    private TransactionService transactions;
    @Autowired
    private AccountService accounts;
    @Autowired
    private BalanceService balances;

    @Test
    public void transactionIsCreated() {
        CreateNewTransactionDto dto = createDto(IN);

        CreateNewTransactionResultDto resultDto = createNewTransaction.create(dto);

        assertTrue(transactions.exists(resultDto.transactionId()));
    }

    @Test
    public void correctAmount() {
        CreateNewTransactionDto dto = createDto(IN);

        CreateNewTransactionResultDto resultDto = createNewTransaction.create(dto);

        Money amount = transactions.findAmount(resultDto.transactionId());
        Money expectedMoney = dto.amount();

        assertEquals(0, amount.amount().compareTo(expectedMoney.amount()));
        assertThat(amount.currency(), is(expectedMoney.currency()));
    }

    @Test
    public void resultDtoBalanceIsCorrectForIncomingTransaction() {
        CreateNewTransactionDto dto = createDto(IN);

        Money balanceBefore = balances.findBalance(dto.accountId(), EUR).orElseThrow();
        assertEquals(balanceBefore.plus(dto.amount()), createNewTransaction.create(dto).newBalance());
    }

    @Test
    public void resultDtoBalanceIsCorrectForOutgoingTransaction() {
        CreateNewTransactionDto dto = createDto(IN);
        AccountId accountId = dto.accountId();
        balances.increase(accountId, new Money(TEN, EUR));

        Money balanceBefore = balances.findBalance(accountId, EUR).orElseThrow();
        assertEquals(balanceBefore.minus(dto.amount()),
                createNewTransaction.create(createDto(accountId, OUT)).newBalance());
    }

    @Test
    public void correctDirection() {
        CreateNewTransactionDto dto = createDto(IN);
        assertThat(transactions.findDirection(createNewTransaction.create(dto).transactionId()),
                is(dto.direction()));
    }

    @Test
    public void correctDescription() {
        CreateNewTransactionDto dto = createDto(IN);
        assertThat(transactions.findDescription(createNewTransaction.create(dto).transactionId()),
                is(dto.description()));
    }

    @Test
    public void failsIfAccountDoesNotExist() {
        AccountId accountId = new AccountId(100000001L);
        assertFalse(accounts.exists(accountId));
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> createNewTransaction.create(createDto(accountId, OUT)));

        assertThat(exception.getMessage(), is(ERROR_ACCOUNT_DOES_NOT_EXIST));
    }

    @Test
    public void failsIfNoBalanceInTransactionCurrency() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> createNewTransaction.create(createDto(OUT, new Money("1", USD))));

        assertThat(exception.getMessage(), is(ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST));
    }

    @Test
    public void failsIfInsufficientFunds() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> createNewTransaction.create(createDto(OUT, new Money("1000000", EUR))));

        assertThat(exception.getMessage(), is(ERROR_NOT_ENOUGH_FUNDS));
    }

    @Test
    public void accountBalanceIsIncreasedIfIncomingTransaction() {
        CreateNewTransactionDto dto = createDto(IN);

        AccountId accountId = dto.accountId();
        Money balanceBefore = balances.findBalance(accountId, EUR).orElseThrow();

        CreateNewTransactionResultDto resultDto = createNewTransaction.create(dto);

        assertEquals(balanceBefore.plus(transactions.findAmount(resultDto.transactionId())),
                balances.findBalance(accountId, EUR).orElseThrow());
    }

    @Test
    public void accountBalanceIsDecreasedIfOutgoingTransaction() {
        AccountId accountId = createAccount();
        balances.increase(accountId, new Money(TEN, EUR));
        Money balanceBefore = balances.findBalance(accountId, EUR).orElseThrow();

        CreateNewTransactionResultDto resultDto = createNewTransaction.create(createDto(accountId, OUT));

        assertEquals(balances.findBalance(accountId, EUR).orElseThrow(),
                balanceBefore.minus(transactions.findAmount(resultDto.transactionId())));
    }

    private CreateNewTransactionDto createDto(TransactionDirection direction) {
        return createDto(createAccount(), direction);
    }

    private CreateNewTransactionDto createDto(TransactionDirection direction, Money amount) {
        return createDto(createAccount(), direction, amount);
    }

    private CreateNewTransactionDto createDto(AccountId accountId, TransactionDirection direction) {
        return createDto(accountId, direction, new Money(TEN, EUR));
    }

    private CreateNewTransactionDto createDto(AccountId accountId, TransactionDirection direction, Money amount) {
        return new CreateNewTransactionDto(accountId, amount, "test_description", direction);
    }

}
