package com.kkoemets.domain.codes;

public enum TransactionStatus {
    NEW("NEW"),
    PROCESSED("PRC"),
    REJECTED("RJT");

    private final String code;

    TransactionStatus(String code) {
        this.code = code;
    }

    public static TransactionStatus create(String code) {
        return TransactionStatus.valueOf(code);
    }

    public String getCode() {
        return code;
    }

}
