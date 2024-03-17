package com.epam.victor.exchanger.service.impl;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.repository.AccountRepository;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;
import com.epam.victor.exchanger.repository.UserRepository;
import com.epam.victor.exchanger.service.ExchangeService;
import com.epam.victor.exchanger.service.exception.AccountNotFoundException;

import java.math.BigDecimal;
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
    public void exchange(String ibanFrom, String ibanTo, CurrencyPair pair, BigDecimal amount) {
        lock.lock();
        Account accountFrom = findAccountByIban(ibanFrom);
        Account accountTo = findAccountByIban(ibanTo);
        CurrencyRate currencyRate = findRateByPair(pair);
        BigDecimal finalAmountOfAccountFrom = accountFrom.getAmount().subtract(amount);
        accountFrom.setAmount(finalAmountOfAccountFrom);
        BigDecimal exchangedAmount = amount.multiply(currencyRate.getRate());
        BigDecimal finalAmountOfAccountTo = accountTo.getAmount().add(exchangedAmount);
        accountTo.setAmount(finalAmountOfAccountTo);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        lock.unlock();
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


}
