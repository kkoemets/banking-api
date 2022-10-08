package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kkoemets.domain.id.TransactionId;

import java.io.IOException;

public class TransactionIdSerializer extends JsonSerializer<TransactionId> {

    @Override
    public void serialize(TransactionId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }

}
