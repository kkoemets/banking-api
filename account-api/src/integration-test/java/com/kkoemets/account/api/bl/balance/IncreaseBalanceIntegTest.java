package com.kkoemets.account.api.bl.balance;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncreaseBalanceIntegTest extends AccountApiIntegTest {

    @Autowired
    private IncreaseBalance increaseBalance;
    @Autowired
    private BalanceService balances;

    @Test
    public void increaseBalance() {
        AccountId accountId = createAccount();
        Money amount = new Money("1.01", EUR);
        Money newBalance = increaseBalance.increase(accountId, amount);

        assertEquals(amount, newBalance);
        assertEquals(amount, balances.findBalance(accountId,
                        amount.currency())
                .stream()
                .findFirst()
                .orElseThrow());
    }

    @Test
    public void failsIfBalanceInCurrencyDoesNotExist() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> increaseBalance.increase(createAccount(), new Money("1", USD)));

        assertThat(exception.getMessage(), is(ERROR_BALANCE_IN_CURRENCY_DOES_NOT_EXIST));
    }

}
