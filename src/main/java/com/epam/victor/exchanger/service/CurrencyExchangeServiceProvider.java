package com.epam.victor.exchanger.service;

import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeCurrencyRateRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeUserRepository;
import com.epam.victor.exchanger.service.impl.CurrencyExchangeService;

public class CurrencyExchangeServiceProvider {
    private static final ExchangeService currencyExchangeService = new CurrencyExchangeService(
            new ExchangeAccountRepository(),
            new ExchangeCurrencyRateRepository(),
            new ExchangeUserRepository()
    );



    public static ExchangeService getExchangeServiceInstance(){
        return currencyExchangeService;
    }
}
