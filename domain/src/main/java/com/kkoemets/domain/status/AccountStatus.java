package com.kkoemets.domain.status;

import static org.apache.logging.log4j.util.Strings.isBlank;

public enum AccountStatus implements CodedEnum {
    ACTIVE("A");

    private final String code;

    AccountStatus(String code) {
        this.code = code;
    }

    public static AccountStatus create(String code) {
        if (isBlank(code)) {
            throw new IllegalArgumentException("Cannot be blank!");
        }

        return AccountStatus.valueOf(code);
    }

    public String getCode() {
        return code;
    }

}
