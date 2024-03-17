package com.epam.victor.exchanger.repository;

import com.epam.victor.exchanger.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(String id);

    List<User> findAll();

}
