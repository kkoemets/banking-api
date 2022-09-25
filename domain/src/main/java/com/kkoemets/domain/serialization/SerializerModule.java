package com.kkoemets.domain.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kkoemets.domain.id.AccountId;

public class SerializerModule extends SimpleModule {

    public SerializerModule() {
        super();
        addSerializer(AccountId.class, new AccountIdSerializer());
        addDeserializer(AccountId.class, new AccountIdDeserializer());
    }


}
