package com.kkoemets.domain.balance;

import org.junit.jupiter.api.Test;

import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    private static final Money TEN_EUROS = new Money("10", EUR);
    private static final Money FIVE_EUROS = new Money("5", EUR);
    private static final Money FIVE_DOLLARS = new Money("5", USD);
    private static final Money FIFTEEN_EUROS = new Money("15", EUR);

    @Test
    public void correctRound() {
        assertThat(TEN_EUROS.amount().toPlainString(), is("10.00"));

        Money moneyWithBigDecimalAmount = new Money(TEN, EUR);
        assertThat(moneyWithBigDecimalAmount.amount().toPlainString(), is("10.00"));
    }

    @Test
    public void equals() {
        assertEquals(new Money(TEN, EUR), TEN_EUROS);
    }

    @Test
    public void minus() {
        assertEquals(FIVE_EUROS, TEN_EUROS.minus(FIVE_EUROS));
        assertEquals(FIVE_EUROS.negate(), TEN_EUROS.negate().minus(FIVE_EUROS.negate()));
        assertEquals(FIFTEEN_EUROS.negate(), TEN_EUROS.negate().minus(FIVE_EUROS));
    }

    @Test
    public void minusFailsIfDifferentCurrencies() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> TEN_EUROS.minus(FIVE_DOLLARS));
        assertTrue(exception.getMessage().contains("Different currencies"));
    }

    @Test
    public void plus() {
        assertEquals(FIFTEEN_EUROS, TEN_EUROS.plus(FIVE_EUROS));
        assertEquals(FIVE_EUROS, TEN_EUROS.plus(FIVE_EUROS.negate()));
        assertEquals(FIFTEEN_EUROS.negate(), TEN_EUROS.negate().plus(FIVE_EUROS.negate()));
    }

    @Test
    public void plusFailsIfDifferentCurrencies() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> TEN_EUROS.plus(FIVE_DOLLARS));
        assertTrue(exception.getMessage().contains("Different currencies"));
    }

    @Test
    public void lessThan() {
        assertTrue(FIVE_EUROS.lessThan(FIFTEEN_EUROS));
        assertFalse(FIFTEEN_EUROS.lessThan(FIVE_EUROS));
        assertFalse(FIVE_EUROS.lessThan(FIVE_EUROS));
    }

    @Test
    public void lessThanFailsIfDifferentCurrencies() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> TEN_EUROS.lessThan(FIVE_DOLLARS));
        assertTrue(exception.getMessage().contains("Different currencies"));
    }

}
