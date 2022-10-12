package com.kkoemets.domain.codes;

public enum TransactionDirection {
    IN("IN"),
    OUT("OUT");

    private final String code;

    TransactionDirection(String code) {
        this.code = code;
    }

    public static TransactionDirection create(String code) {
        return TransactionDirection.valueOf(code);
    }

    public String getCode() {
        return code;
    }

}
