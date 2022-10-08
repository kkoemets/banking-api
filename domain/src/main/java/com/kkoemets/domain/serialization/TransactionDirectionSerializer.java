package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kkoemets.domain.codes.TransactionDirection;

import java.io.IOException;

public class TransactionDirectionSerializer extends JsonSerializer<TransactionDirection> {

    @Override
    public void serialize(TransactionDirection value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getCode());
    }

}
