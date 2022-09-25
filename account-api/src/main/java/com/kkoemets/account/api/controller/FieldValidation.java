package com.kkoemets.account.api.controller;

public class FieldValidation {
    public static final int MAX_CURRENCIES = 5;
    public static final int MIN_CUSTOMER_ID = 1;
    public static final String COUNTRY_CODE_REGEX = "^[A-Z]{2}\\z";
    public static final String CURRENCY_REGEX = "^[A-Z]{3}\\z";

}
