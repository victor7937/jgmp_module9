package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.repository.exception.JsonFileReadException;
import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}
