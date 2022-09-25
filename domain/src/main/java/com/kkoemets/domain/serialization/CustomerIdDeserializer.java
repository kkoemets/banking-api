package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kkoemets.domain.id.CustomerId;

import java.io.IOException;

public class CustomerIdDeserializer extends JsonDeserializer<CustomerId> {

    @Override
    public CustomerId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new CustomerId(p.getLongValue());
    }

}
