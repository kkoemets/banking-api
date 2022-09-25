package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kkoemets.domain.codes.Currency;

import java.io.IOException;

public class CurrencyDeserializer extends JsonDeserializer<Currency> {

    @Override
    public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Currency.create(p.getValueAsString());
    }

}
