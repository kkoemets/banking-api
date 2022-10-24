package com.kkoemets.account.api.bl.balance;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST;
import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_NOT_ENOUGH_FUNDS;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecreaseBalanceIntegTest extends AccountApiIntegTest {

    @Autowired
    private DecreaseBalance decreaseBalance;
    @Autowired
    private IncreaseBalance increaseBalance;
    @Autowired
    private BalanceService balances;

    @Test
    public void decreaseBalance() {
        Money amount = new Money(TEN, EUR);
        AccountId accountId = createAccount();
        increaseBalance.increase(accountId, amount);

        Money newBalance = decreaseBalance.decrease(accountId, amount);

        Money expectedAmount = new Money("0.00", EUR);
        assertEquals(expectedAmount, newBalance);
        assertEquals(expectedAmount,
                balances.findBalance(accountId, amount.currency())
                        .stream()
                        .findFirst()
                        .orElseThrow());
    }

    @Test
    public void failsIfBalanceInCurrencyDoesNotExist() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> decreaseBalance.decrease(createAccount(), new Money("1", USD)));

        assertThat(exception.getMessage(), is(ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST));
    }

    @Test
    public void failsIfNotEnoughFunds() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> decreaseBalance.decrease(createAccount(), new Money(TEN, EUR)));

        assertThat(exception.getMessage(), is(ERROR_NOT_ENOUGH_FUNDS));
    }

}
