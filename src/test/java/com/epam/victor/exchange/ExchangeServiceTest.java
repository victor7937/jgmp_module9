package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.service.CurrencyExchangeServiceProvider;
import com.epam.victor.exchanger.service.ExchangeService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class ExchangeServiceTest {

    @Test
    void currencyShouldBeExchangedBetweenAccounts(){
        ExchangeService service = CurrencyExchangeServiceProvider.getExchangeServiceInstance();
        service.exchangeBetweenAccounts(
                "GE29NB0000000101904912",
                "GE29NB0000000101904914",
                CurrencyPair.of("USD/GEL"),
                BigDecimal.valueOf(100));
    }

    @Test
    void accountShouldBeWritten(){
        ExchangeService exchangeService = CurrencyExchangeServiceProvider.getExchangeServiceInstance();
        exchangeService.saveAccount(new Account(
                        "GE29NB0000000101904910",
                        Currency.getInstance("GEL"),
                        BigDecimal.valueOf(200)));

    }
    @Test
    void userShouldBeWritten(){
        ExchangeService exchangeService = CurrencyExchangeServiceProvider.getExchangeServiceInstance();
        User user = new User("Victor Vyrostak");
        user.setAccounts(List.of(
                new Account(
                        "GE29NB0000000101904915",
                        Currency.getInstance("GEL"),
                        BigDecimal.valueOf(3000)),
                new Account(
                        "GE29NB0000000101904916",
                        Currency.getInstance("USD"),
                        BigDecimal.valueOf(10000))
                ));
        exchangeService.saveUser(user);

    }

}

