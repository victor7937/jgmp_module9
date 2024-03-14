package com.epam.victor.exchanger.repository;

import com.epam.victor.exchanger.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account crete(Account account);

    Optional<Account> findByIban(String iban);

    List<Account> findAll();
}
