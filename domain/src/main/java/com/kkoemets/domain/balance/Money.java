package com.kkoemets.domain.balance;

import com.kkoemets.domain.codes.Currency;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public record Money(BigDecimal amount, Currency currency) {

    public static Money zero(Currency currency) {
        return new Money(ZERO, currency);
    }

}
