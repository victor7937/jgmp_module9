package com.epam.victor.exchanger.repository.impl;

import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.repository.UserRepository;
import com.epam.victor.exchanger.repository.json.JsonFileUtil;
import com.epam.victor.exchanger.repository.json.ObjectMapperConfigurer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExchangeUserRepository implements UserRepository {

    private ObjectMapper objectMapper;

    private final static String USER_PATH = "C:\\Users\\Victor_Vyrostak\\Documents\\Mentoring\\Module9_Multithreading\\exchange_app_data\\User\\";

    public ExchangeUserRepository() {
        objectMapper = ObjectMapperConfigurer.configure(new ObjectMapper());
    }

    @Override
    public void save(User user) {
        JsonFileUtil.writeObjectToFile(
                user,
                USER_PATH + user.getId() + ".json",
                objectMapper,
                new TypeReference<>() {});
    }

    @Override
    public Optional<User> findById(String id) {
        return JsonFileUtil.fileByPathToObject(USER_PATH + id + ".json", objectMapper, new TypeReference<>() {});
    }

    @Override
    public List<User> findAll() {
        return JsonFileUtil.findAllFromFolder(USER_PATH, objectMapper, new TypeReference<>() {});
    }

    @Override
    public void removeUser(String id) {
        JsonFileUtil.removeFile(USER_PATH + id + ".json");
    }
}
