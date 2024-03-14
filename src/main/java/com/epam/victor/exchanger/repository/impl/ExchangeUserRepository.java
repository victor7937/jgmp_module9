package com.epam.victor.exchanger.repository.impl;

import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ExchangeUserRepository implements UserRepository {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
