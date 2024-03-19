package com.epam.victor.exchanger.service;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.model.User;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public interface ExchangeService {

    void exchangeBetweenAccounts(String ibanFrom, String ibanTo, CurrencyPair pair, BigDecimal amount);

    void exchangeBetweenUsers(String idFrom, String idTo, CurrencyPair pair, BigDecimal amount);

    Account findAccountByIban(String iban);

    CurrencyRate findRateByPair(CurrencyPair currencyPair);

    User findUserById(String id);

    List<Account> findAccountsOfUser(Currency currency, String userId);

    Optional<Account> findAccountOfUser(String iban, String userId);

}
