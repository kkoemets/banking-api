package com.kkoemets.domain.balance;

import com.kkoemets.domain.codes.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public final class Money {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = HALF_UP;
    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        this.amount = setupAmount(amount);
        this.currency = currency;
    }

    public Money(String amount, Currency currency) {
        this.amount = setupAmount(new BigDecimal(amount));
        this.currency = currency;
    }

    public static Money zero(Currency currency) {
        return new Money(ZERO, currency);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(amount, currency);
    }

    public BigDecimal amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Money) obj;
        return Objects.equals(this.amount, that.amount) &&
                Objects.equals(this.currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    public Money minus(Money that) {
        validateCurrencies(that);

        return new Money(amount.subtract(that.amount), currency);
    }

    public Money plus(Money that) {
        return minus(that.negate());
    }

    public Money negate() {
        return new Money(amount.negate(), currency);
    }

    public boolean lessThan(Money that) {
        validateCurrencies(that);
        return this.amount.compareTo(that.amount) < 0;
    }

    private BigDecimal setupAmount(BigDecimal amount) {
        return amount.setScale(SCALE, ROUNDING);
    }

    private void validateCurrencies(Money that) {
        Currency thisCurrency = currency;
        Currency thatCurrency = that.currency;
        if (!thatCurrency.equals(thisCurrency)) {
            throw new RuntimeException("Different currencies! This-%s, that-%s".formatted(thisCurrency, thatCurrency));
        }
    }

}
