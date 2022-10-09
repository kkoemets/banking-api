package com.kkoemets.domain.codes;

import java.util.Arrays;

public enum TransactionStatus {
    NEW("NEW"),
    PROCESSED("PRC"),
    REJECTED("RJT");

    private final String code;

    TransactionStatus(String code) {
        this.code = code;
    }

    public static TransactionStatus create(String code) {
        return Arrays
                .stream(TransactionStatus.values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow();
    }

    public String getCode() {
        return code;
    }

}
