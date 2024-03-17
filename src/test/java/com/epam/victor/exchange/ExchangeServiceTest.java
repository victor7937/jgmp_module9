package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.service.CurrencyExchangeServiceProvider;
import com.epam.victor.exchanger.service.ExchangeService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ExchangeServiceTest {

    @Test
    void currencyShouldBeExchangedBetweenAccounts(){
        ExchangeService service = CurrencyExchangeServiceProvider.getExchangeServiceInstance();
        service.exchange(
                "GE29NB0000000101904912",
                "GE29NB0000000101904914",
                CurrencyPair.of("USD/GEL"),
                BigDecimal.valueOf(100));
    }
}

