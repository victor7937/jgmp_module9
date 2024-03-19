package com.epam.victor.exchanger.service.impl;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.repository.AccountRepository;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;
import com.epam.victor.exchanger.repository.UserRepository;
import com.epam.victor.exchanger.service.ExchangeService;
import com.epam.victor.exchanger.service.exception.AccountNotFoundException;
import com.epam.victor.exchanger.service.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class CurrencyExchangeService implements ExchangeService {

    private final AccountRepository accountRepository;

    private final CurrencyRateRepository currencyRateRepository;

    private final UserRepository userRepository;

    private final ReentrantLock lock = new ReentrantLock();

    public CurrencyExchangeService(AccountRepository accountRepository, CurrencyRateRepository currencyRateRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.currencyRateRepository = currencyRateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void exchangeBetweenAccounts(String ibanFrom, String ibanTo, CurrencyPair pair, BigDecimal amount) {
        try {
            lock.lock();
            Account accountFrom = findAccountByIban(ibanFrom);
            Account accountTo = findAccountByIban(ibanTo);
            exchangeBetweenAccounts(accountFrom, accountTo, pair, amount);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void exchangeBetweenUsers(String idFrom, String idTo, CurrencyPair pair, BigDecimal amount) {
        try {
            lock.lock();

            List<Account> accountsOfUserFrom = findAccountsOfUser(pair.getBase(), idFrom);
            List<Account> accountsOfUserTo = findAccountsOfUser(pair.getQuote(), idTo);

            if (accountsOfUserFrom.isEmpty()) {
                throw new AccountNotFoundException(String.format(
                        "No accounts for of %s currency for user %s",
                        pair.getBase(),
                        idFrom));
            }

            if (accountsOfUserTo.isEmpty()) {
                throw new AccountNotFoundException(String.format(
                        "No accounts for of %s currency for user %s",
                        pair.getQuote(),
                        idTo));
            }

            Account accountFrom = accountsOfUserFrom.stream()
                    .filter(a -> a.getAmount().compareTo(amount) >= 0)
                    .findAny()
                    .orElseThrow(() -> new InsufficientFundsException(String.format(
                            "Not able to withdraw %s for user %s Reason: insufficient funds",
                            amount,
                            idFrom)));

            Account accountTo = accountsOfUserTo.get(0);

            exchangeBetweenAccounts(accountFrom, accountTo, pair, amount);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Account findAccountByIban(String iban) {
        return accountRepository.findByIban(iban).orElseThrow(() ->
                new AccountNotFoundException("Account of iban " + iban + " not found"));

    }

    @Override
    public CurrencyRate findRateByPair(CurrencyPair currencyPair) {
        return currencyRateRepository.findByCurrencyPair(currencyPair).orElseThrow(() ->
                new AccountNotFoundException("Currency rate of currency pair " + currencyPair.toString() + " not found"));
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new AccountNotFoundException("User of id " + id + " not found"));
    }

    @Override
    public List<Account> findAccountsOfUser(Currency currency, String userId) {
        return findUserById(userId)
                .getAccounts()
                .stream()
                .filter(a -> a.getCurrency().equals(currency))
                .toList();
    }

    @Override
    public Optional<Account> findAccountOfUser(String iban, String userId) {
        return findUserById(userId)
                .getAccounts()
                .stream()
                .filter(a -> a.getIban().equals(iban))
                .findAny();
    }


    private void exchangeBetweenAccounts(Account accountFrom, Account accountTo, CurrencyPair pair, BigDecimal amount) {
        CurrencyRate currencyRate = findRateByPair(pair);
        BigDecimal finalAmountOfAccountFrom = accountFrom.getAmount().subtract(amount);
        accountFrom.setAmount(finalAmountOfAccountFrom);
        BigDecimal exchangedAmount = amount.multiply(currencyRate.getRate());
        BigDecimal finalAmountOfAccountTo = accountTo.getAmount().add(exchangedAmount);
        accountTo.setAmount(finalAmountOfAccountTo);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }


}
