package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.repository.impl.ExchangeAccountRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class WriteFileTest {

    @Test
    void FileShouldBeWritten(){
        ExchangeAccountRepository exchangeAccountRepository = new ExchangeAccountRepository();
        exchangeAccountRepository.crete(new Account(
                "GE27NB0000000101904917",
                Currency.getInstance("GEL"),
                BigDecimal.valueOf(30)));

    }
}
