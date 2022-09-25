package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kkoemets.domain.id.AccountId;

import java.io.IOException;

public class AccountIdDeserializer extends JsonDeserializer<AccountId> {

    @Override
    public AccountId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new AccountId(p.getLongValue());
    }

}
