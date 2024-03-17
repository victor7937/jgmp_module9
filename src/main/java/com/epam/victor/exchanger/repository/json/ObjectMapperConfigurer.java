package com.epam.victor.exchanger.repository.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperConfigurer {


    public static ObjectMapper configure(ObjectMapper objectMapper) {
//        AccountListDeserializer accountListDeserializer = new AccountListDeserializer();
//        accountListDeserializer.setObjectMapper(objectMapper);
//        AccountListSerializer accountListSerializer = new AccountListSerializer();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(List.class, accountListDeserializer);
//        module.addSerializer(List.class, accountListSerializer);
//        objectMapper.registerModule(module);
        return objectMapper;
    }

}
