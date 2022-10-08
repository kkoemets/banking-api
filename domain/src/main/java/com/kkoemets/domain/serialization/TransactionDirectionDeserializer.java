package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kkoemets.domain.codes.TransactionDirection;

import java.io.IOException;

public class TransactionDirectionDeserializer extends JsonDeserializer<TransactionDirection> {

    @Override
    public TransactionDirection deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        return TransactionDirection.valueOf(value);
    }

}
