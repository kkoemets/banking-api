package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kkoemets.domain.codes.CountryIsoCode2;

import java.io.IOException;

public class CountryIsoCode2Serializer extends JsonSerializer<CountryIsoCode2> {

    @Override
    public void serialize(CountryIsoCode2 value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }

}
