package com.epam.victor.exchanger.repository.impl;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.repository.AccountRepository;
import com.epam.victor.exchanger.repository.json.JsonFileUtil;
import com.epam.victor.exchanger.repository.json.ObjectMapperConfigurer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class ExchangeAccountRepository implements AccountRepository {


    private ObjectMapper objectMapper;

    private final static String ACCOUNT_PATH = "C:\\Users\\Victor_Vyrostak\\Documents\\Mentoring\\Module9_Multithreading\\exchange_app_data\\Account\\";

    public ExchangeAccountRepository() {
        this.objectMapper = ObjectMapperConfigurer.configure(new ObjectMapper());
    }

    @Override
    public void save(Account account) {
        JsonFileUtil.writeObjectToFile(
                account,
                ACCOUNT_PATH + account.getIban() + ".json",
                objectMapper,
                new TypeReference<>() {});
    }

    @Override
    public Optional<Account> findByIban(String iban) {
        return JsonFileUtil.fileByPathToObject(ACCOUNT_PATH + iban + ".json", objectMapper, new TypeReference<>() {});

    }

    @Override
    public List<Account> findAll() {
        return JsonFileUtil.findAllFromFolder(ACCOUNT_PATH, objectMapper, new TypeReference<>() {});
    }
}
