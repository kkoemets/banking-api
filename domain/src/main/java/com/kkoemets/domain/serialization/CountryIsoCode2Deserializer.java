package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kkoemets.domain.codes.CountryIsoCode2;

import java.io.IOException;

public class CountryIsoCode2Deserializer extends JsonDeserializer<CountryIsoCode2> {

    @Override
    public CountryIsoCode2 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return CountryIsoCode2.create(p.getValueAsString());
    }

}
