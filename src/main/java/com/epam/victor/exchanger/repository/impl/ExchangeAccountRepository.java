package com.epam.victor.exchanger.repository.impl;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.repository.AccountRepository;
import com.epam.victor.exchanger.repository.exception.JsonFileNotCreatedException;
import com.epam.victor.exchanger.repository.exception.JsonFileReadException;
import com.epam.victor.exchanger.repository.json.ObjectMapperConfigurer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeAccountRepository implements AccountRepository {


    private ObjectMapper objectMapper;

    private final static String ACCOUNT_PATH = "/home/victor/Documents/EPAM/Mentoring/9/exchange_app_data/Account/";

    public ExchangeAccountRepository() {
        this.objectMapper = ObjectMapperConfigurer.configure(new ObjectMapper());
    }

    @Override
    public Account crete(Account account) {
        try {
            objectMapper.writerFor(new TypeReference<Account>() {}).writeValue(
                    new File(ACCOUNT_PATH + account.getIban()), account);
        } catch (IOException e) {
            throw new JsonFileNotCreatedException("File for iban " + account.getIban() + " wasn't created", e);
        }

        return null;
    }

    @Override
    public Optional<Account> findByIban(String iban) {
        Account account;
        try {
            account = objectMapper.readValue(new File(ACCOUNT_PATH + iban), new TypeReference<>() {});
        } catch (FileNotFoundException e){
            account = null;
        } catch (IOException e) {
            throw new JsonFileNotCreatedException("Unable to read file for iban " + iban, e);
        }
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts;
        try (Stream<Path> paths = Files.walk(Paths.get(ACCOUNT_PATH))) {
            accounts = paths
                    .filter(Files::isRegularFile)
                    .map(p -> {
                        try {
                            return objectMapper.<Account>readValue(p.toFile(), new TypeReference<>() {});
                        } catch (IOException e) {
                            throw new JsonFileReadException("Unable to find the file of iban " + p.getFileName().toString());
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new JsonFileReadException("List of account read exception", e);
        }
        return accounts;
    }
}
