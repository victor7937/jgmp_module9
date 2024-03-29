package com.epam.victor.exchanger.service;

import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeCurrencyRateRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeUserRepository;
import com.epam.victor.exchanger.service.impl.CurrencyExchangeService;
import com.epam.victor.exchanger.service.util.LockManager;

public class CurrencyExchangeServiceProvider {
    private static final ExchangeService currencyExchangeService = new CurrencyExchangeService(
            new ExchangeAccountRepository(),
            new ExchangeCurrencyRateRepository(),
            new ExchangeUserRepository(),
            new LockManager()
    );



    public static ExchangeService getExchangeServiceInstance(){
        return currencyExchangeService;
    }
}
