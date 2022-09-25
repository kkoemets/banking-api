package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;

public class SerializerModule extends SimpleModule {

    public SerializerModule() {
        super();
        add(AccountId.class, new AccountIdSerializer(), new AccountIdDeserializer());
        add(CustomerId.class, new CustomerIdSerializer(), new CustomerIdDeserializer());
        add(Currency.class, new CurrencySerializer(), new CurrencyDeserializer());
    }

    private <T> void add(Class<T> type, JsonSerializer<T> ser, JsonDeserializer<T> deser) {
        addSerializer(type, ser);
        addDeserializer(type, deser);
    }

}
