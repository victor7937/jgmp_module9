package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;
import com.epam.victor.exchanger.repository.UserRepository;
import com.epam.victor.exchanger.repository.exception.JsonFileReadException;
import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeCurrencyRateRepository;
import com.epam.victor.exchanger.repository.impl.ExchangeUserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class ReadFileTest {

    @Test
    void accountFileShouldBeRead (){
        ExchangeAccountRepository exchangeAccountRepository = new ExchangeAccountRepository();
        Account account = exchangeAccountRepository.findByIban("GE29NB0000000101904917").orElseGet(Account::new);
        System.out.println(account);
    }

    @Test
    void accountFilesListShouldBeRead(){
        ExchangeAccountRepository exchangeAccountRepository = new ExchangeAccountRepository();
        List<Account> accounts = exchangeAccountRepository.findAll();
        System.out.println(accounts);
    }

    @Test
    void userShouldBeReadById(){
        UserRepository userRepository = new ExchangeUserRepository();
        User user = userRepository.findById("d8523e5a-5fcd-44c6-beed-d3ba2a10b202").get();
        System.out.println(user);
    }

    @Test
    void userFilesListShouldBeRead(){
        ExchangeUserRepository exchangeUserRepository = new ExchangeUserRepository();
        List<User> users = exchangeUserRepository.findAll();
        System.out.println(users);
    }

    @Test
    void currencyRateShouldBeReadByPair(){
        CurrencyRateRepository currencyRateRepository = new ExchangeCurrencyRateRepository();
        CurrencyRate currencyRate = currencyRateRepository.findByCurrencyPair(CurrencyPair.of("GEL/USD")).get();
        System.out.println(currencyRate);

    }

    @Test
    void allCurrencyRatesShouldBeRead(){
        CurrencyRateRepository currencyRateRepository = new ExchangeCurrencyRateRepository();
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        System.out.println(currencyRateList);

    }


}
