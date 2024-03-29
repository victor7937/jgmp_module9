package com.epam.victor.exchanger.service.impl;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.repository.AccountRepository;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;
import com.epam.victor.exchanger.repository.UserRepository;
import com.epam.victor.exchanger.service.ExchangeService;
import com.epam.victor.exchanger.service.exception.AccountAlreadyExistException;
import com.epam.victor.exchanger.service.exception.AccountNotFoundException;
import com.epam.victor.exchanger.service.exception.InsufficientFundsException;
import com.epam.victor.exchanger.service.util.LockManager;
import com.epam.victor.exchanger.service.util.LockPair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class CurrencyExchangeService implements ExchangeService {

    private final AccountRepository accountRepository;

    private final CurrencyRateRepository currencyRateRepository;

    private final UserRepository userRepository;

    private final LockManager lockManager;

    public CurrencyExchangeService(AccountRepository accountRepository,
                                   CurrencyRateRepository currencyRateRepository,
                                   UserRepository userRepository,
                                   LockManager lockManager) {
        this.accountRepository = accountRepository;
        this.currencyRateRepository = currencyRateRepository;
        this.userRepository = userRepository;
        this.lockManager = lockManager;
    }

    @Override
    public void exchangeBetweenAccounts(String ibanFrom, String ibanTo, CurrencyPair pair, BigDecimal amount) {
        LockPair lockPair = lockManager.getPair(ibanFrom, ibanTo);
        lockManager.lockFirst(lockPair);
        try {
            lockManager.lockSecond(lockPair);
            try {
                Account accountFrom = findAccountByIban(ibanFrom);
                Account accountTo = findAccountByIban(ibanTo);
                if (accountFrom.getAmount().compareTo(amount) < 0) {
                    throw new InsufficientFundsException(String.format(
                            "Not able to withdraw %s for account %s Reason: insufficient funds", amount, ibanFrom));
                }
                exchangeBetweenAccounts(accountFrom, accountTo, pair, amount);
            } finally {
               lockManager.unlockAndReleaseSecond(lockPair);
            }

        } finally {
           lockManager.unlockAndReleaseFirst(lockPair);
        }
    }

    @Override
    public void exchangeBetweenUsers(String idFrom, String idTo, CurrencyPair pair, BigDecimal amount) {
        LockPair lockPair = lockManager.getPair(idFrom, idTo);
        lockManager.lockFirst(lockPair);
        try {
             lockManager.lockSecond(lockPair);
             try {
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
                 lockManager.unlockAndReleaseSecond(lockPair);
             }
        } finally {
            lockManager.unlockAndReleaseFirst(lockPair);
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

    @Override
    public Account saveAccount(Account account) {
        String iban = account.getIban();
        //if(iban == null || !IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(iban)){
        if(iban == null || iban.isEmpty()){
            throw new IllegalArgumentException("Iban is not valid");
        }
        accountRepository.save(account);
        return findAccountByIban(iban);
    }

    @Override
    public User saveUser(User user) {
        String uuid = UUID.randomUUID().toString();
        if (user.getId() == null || user.getId().isEmpty()){
            user.setId(uuid);
        } else {
            uuid = user.getId();
        }
        if (user.getAccounts() == null){
            user.setAccounts(new ArrayList<>());
        }
        user.getAccounts().forEach(a -> {
            if(accountRepository.isAccountExist(a.getIban())){
                throw new AccountAlreadyExistException(
                        String.format("Account %s is already created",  a.getIban()));
            }
        });
        user.getAccounts().forEach(accountRepository::save);
        userRepository.save(user);
        return findUserById(uuid);
    }

    @Override
    public void saveCurrencyRate(CurrencyRate currencyRate) {
        currencyRateRepository.save(currencyRate);
    }

    @Override
    public void removeAccount(String iban) {
        accountRepository.removeAccount(iban);
    }

    @Override
    public void removeUser(String id) {
        User user = findUserById(id);
        user.getAccounts().forEach(a -> accountRepository.removeAccount(a.getIban()));
        userRepository.removeUser(id);
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
