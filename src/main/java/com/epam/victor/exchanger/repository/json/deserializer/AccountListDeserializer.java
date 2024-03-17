package com.epam.victor.exchanger.repository.json.deserializer;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.repository.exception.JsonFileReadException;
import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AccountListDeserializer extends StdDeserializer<List<Account>> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AccountListDeserializer() {
        this(null);
    }

    @Override
    public List<Account> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        List<String> ibanList = objectMapper.treeToValue(node, new TypeReference<>() {});
        ExchangeAccountRepository exchangeAccountRepository = new ExchangeAccountRepository();

        return ibanList.stream()
                .map(i -> exchangeAccountRepository.findByIban(i).orElseThrow(
                        () -> new JsonFileReadException("Iban Not found")))
                .collect(Collectors.toList());
    }

    protected AccountListDeserializer(Class<?> vc) {
        super(vc);
    }

}