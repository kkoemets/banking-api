package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kkoemets.domain.id.TransactionId;

import java.io.IOException;

public class TransactionIdDeserializer extends JsonDeserializer<TransactionId> {

    @Override
    public TransactionId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new TransactionId(p.getLongValue());
    }

}
