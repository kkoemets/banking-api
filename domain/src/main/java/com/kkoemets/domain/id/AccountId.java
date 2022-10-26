package com.kkoemets.domain.id;

public class AccountId extends Id<AccountId> {

    public AccountId(Long value) {
        super(value);
    }

    public AccountId(String value) {
        super(Long.valueOf(value));
    }

}
