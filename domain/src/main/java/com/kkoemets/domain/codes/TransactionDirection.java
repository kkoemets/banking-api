package com.kkoemets.domain.codes;

public enum TransactionDirection {
    IN("IN"),
    OUT("OUT");

    private final String code;

    TransactionDirection(String code) {
        this.code = code;
    }

    private static TransactionDirection create(String countryCode) {
        return TransactionDirection.valueOf(countryCode);
    }

    public String getCode() {
        return code;
    }

}
