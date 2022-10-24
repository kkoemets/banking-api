package com.kkoemets.account.api.controller;

public class FieldValidation {
    public static final int MAX_CURRENCIES = 5;
    public static final int MIN_CUSTOMER_ID = 1;
    public static final int MIN_ACCOUNT_ID = 1;
    public static final int DESCRIPTION_LENGTH = 140;

    public static class Regex {
        public static final String COUNTRY_CODE = "^[A-Z]{2}\\z";
        public static final String CURRENCY = "^[A-Z]{3}\\z";
        public static final String TRANSACTION_AMOUNT = "^[0-9]*(\\.[0-9]{0,2})?$";
        public static final String AMOUNT_MORE_THAN_ZERO = "^([0-9]*[1-9][0-9]*(\\.[0-9]+)?|[0]+\\.[0-9]*[1-9][0-9]*)";
        public static final String TRANSACTION_DIRECTION = "^(IN|OUT)$";

    }

}
