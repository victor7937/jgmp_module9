package com.epam.victor.exchanger.repository;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User user);

    Optional<User> findByName(String name);

    Optional<User> findById(Long id);

    List<User> findAll();

}
