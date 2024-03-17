package com.epam.victor.exchanger.repository.json.serializer;

import com.epam.victor.exchanger.model.Account;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class AccountListSerializer extends StdSerializer<List<Account>> {

    public AccountListSerializer() {
        this(null);
    }

    protected AccountListSerializer(Class<List<Account>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Account> accounts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Account account : accounts) {
            jsonGenerator.writeString(account.getIban());
        }
        jsonGenerator.writeEndArray();
    }
}
