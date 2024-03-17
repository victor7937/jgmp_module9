package com.epam.victor.exchanger.service;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;

import java.math.BigDecimal;

public interface ExchangeService {

    void exchange(String ibanFrom, String ibanTo, CurrencyPair pair, BigDecimal amount);

    Account findAccountByIban(String iban);

    CurrencyRate findRateByPair(CurrencyPair currencyPair);


}
