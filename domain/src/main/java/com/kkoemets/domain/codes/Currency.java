package com.kkoemets.domain.codes;

import static org.apache.commons.lang3.StringUtils.isAlpha;

public class Currency extends StringValue {
    private static final int CURRENCY_LENGTH = 3;
    public static final Currency EUR = create("EUR");

    private Currency(String currency) {
        super(currency);
    }

    public static Currency create(String currency) {
        if (!isAlpha(currency) || currency.length() != CURRENCY_LENGTH) {
            throw new IllegalArgumentException("Invalid currency-" + currency);
        }

        return new Currency(currency.toUpperCase());
    }

}
