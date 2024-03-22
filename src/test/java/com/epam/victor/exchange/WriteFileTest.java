package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.repository.AccountRepository;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;
import com.epam.victor.exchanger.repository.UserRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeCurrencyRateRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeUserRepository;
import com.epam.victor.exchanger.service.ExchangeService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public class WriteFileTest {

    @Test
    void accountShouldBeWritten(){
        ExchangeAccountRepository exchangeAccountRepository = new ExchangeAccountRepository();
        exchangeAccountRepository.save(new Account(
                "GE29NB0000000101904914",
                Currency.getInstance("GEL"),
                BigDecimal.valueOf(150)));
    }



    @Test
    void userShouldBeWritten(){
        UserRepository userRepository = new ExchangeUserRepository();
        AccountRepository accountRepository = new ExchangeAccountRepository();
        userRepository.save(new User(UUID.randomUUID().toString(), "Akaki Kvernadze",
                List.of(accountRepository.findByIban("GE29NB0000000101904913").get(),
                        accountRepository.findByIban("GE29NB0000000101904914").get())));
    }

    @Test
    void currencyRateShouldBeCreated(){
        CurrencyRate currencyRate = new CurrencyRate(CurrencyPair.of("GEL/USD"), BigDecimal.valueOf(2.659));
        CurrencyRateRepository currencyRateRepository = new ExchangeCurrencyRateRepository();
        currencyRateRepository.save(currencyRate);
    }
}
