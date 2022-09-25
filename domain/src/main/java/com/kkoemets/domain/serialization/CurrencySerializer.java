package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kkoemets.domain.codes.Currency;

import java.io.IOException;

public class CurrencySerializer extends JsonSerializer<Currency> {

    @Override
    public void serialize(Currency value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getValue());
    }

}
