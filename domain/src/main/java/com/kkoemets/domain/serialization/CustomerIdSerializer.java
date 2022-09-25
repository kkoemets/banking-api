package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kkoemets.domain.id.CustomerId;

import java.io.IOException;

public class CustomerIdSerializer extends JsonSerializer<CustomerId> {

    @Override
    public void serialize(CustomerId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }

}
