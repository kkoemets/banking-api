package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kkoemets.domain.id.AccountId;

import java.io.IOException;

public class AccountIdSerializer extends JsonSerializer<AccountId> {

    @Override
    public void serialize(AccountId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }

}
