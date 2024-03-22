package com.epam.victor.exchange;

import com.epam.victor.exchanger.model.Account;
import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.model.User;
import com.epam.victor.exchanger.service.CurrencyExchangeServiceProvider;
import com.epam.victor.exchanger.service.ExchangeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MultithreadExecutionTest {

    @Test
    void asyncExecutionBetweenAccountShouldBeCorrect(){
        String accountGel = "GE29NB0000000101904917";
        String accountUsd = "GE29NB0000000101904918";
        BigDecimal rateValue = BigDecimal.valueOf(2.6);
        CurrencyPair currencyPair = CurrencyPair.of("USD/GEL");
        BigDecimal initialAmountGel = BigDecimal.valueOf(3000);
        BigDecimal initialAmountUsd = BigDecimal.valueOf(10000);
        BigDecimal valeToExchange = BigDecimal.valueOf(10);
        int threadCount = 10;
        int transferCount = 500;

        User user = new User("Test Test");
        user.setAccounts(List.of(
                new Account(
                        accountGel,
                        Currency.getInstance("GEL"),
                        initialAmountGel),
                new Account(
                        accountUsd,
                        Currency.getInstance("USD"),
                        initialAmountUsd)
        ));
        CurrencyRate rate = new CurrencyRate(currencyPair, rateValue);

        ExchangeService service = CurrencyExchangeServiceProvider.getExchangeServiceInstance();
        user = service.saveUser(user);
        service.saveCurrencyRate(rate);

        printAccountsState(accountGel, accountUsd, service);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        IntStream.range(0, transferCount).forEach((i) -> {
            executorService.submit(() -> {
                service.exchangeBetweenAccounts(accountUsd, accountGel, currencyPair, valeToExchange);
            });
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printAccountsState(accountGel, accountUsd, service);


        BigDecimal actualAmountGel = service.findAccountByIban(accountGel).getAmount();
        BigDecimal actualAmountUSD = service.findAccountByIban(accountUsd).getAmount();
        BigDecimal expectedAmountGel = initialAmountGel.add(valeToExchange.multiply(rateValue).multiply(BigDecimal.valueOf(transferCount)));

        BigDecimal expectedAmountUsd = initialAmountUsd.subtract(valeToExchange.multiply(BigDecimal.valueOf(transferCount)));
        System.out.println(expectedAmountUsd);
        Assertions.assertEquals(0, actualAmountGel.compareTo(expectedAmountGel));
        Assertions.assertEquals(0, actualAmountUSD.compareTo(expectedAmountUsd));

        service.removeUser(user.getId());


    }

    @Test
    void asyncExecutionBetweenUsersShouldBeCorrect(){
        String accountGel = "GE29NB0000000101904917";
        String accountUsd = "GE29NB0000000101904918";
        BigDecimal rateValue = BigDecimal.valueOf(2.6);
        CurrencyPair currencyPair = CurrencyPair.of("USD/GEL");
        BigDecimal initialAmountGel = BigDecimal.valueOf(3000);
        BigDecimal initialAmountUsd = BigDecimal.valueOf(10000);
        BigDecimal valeToExchange = BigDecimal.valueOf(10);
        int threadCount = 10;
        int transferCount = 500;

        User userGel = new User("Test Test1");
        userGel.setAccounts(List.of(
                new Account(
                        accountGel,
                        Currency.getInstance("GEL"),
                        initialAmountGel),
                new Account(
                        "GE29NB0000000101904920",
                        Currency.getInstance("EUR"),
                        BigDecimal.valueOf(1000)),
                new Account(
                        "GE29NB0000000101904921",
                        Currency.getInstance("GBP"),
                        BigDecimal.valueOf(500))));

        User userUsd = new User("Test Test2");
        userUsd.setAccounts(List.of(
                new Account(
                        accountUsd,
                        Currency.getInstance("USD"),
                        initialAmountUsd),
                new Account(
                        "GE29NB0000000101904930",
                        Currency.getInstance("KZT"),
                        BigDecimal.valueOf(300000)),
                new Account(
                        "GE29NB0000000101904931",
                        Currency.getInstance("CNY"),
                        BigDecimal.valueOf(793.5))
        ));
        CurrencyRate rate = new CurrencyRate(currencyPair, rateValue);

        ExchangeService service = CurrencyExchangeServiceProvider.getExchangeServiceInstance();
        String userUsdId = service.saveUser(userUsd).getId();
        String userGelId = service.saveUser(userGel).getId();

        service.saveCurrencyRate(rate);

        printAccountsState(accountGel, accountUsd, service);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        IntStream.range(0, transferCount).forEach((i) -> {
            executorService.submit(() -> {
                service.exchangeBetweenUsers(userUsdId, userGelId, currencyPair, valeToExchange);
            });
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printAccountsState(accountGel, accountUsd, service);


        BigDecimal actualAmountGel = service.findAccountByIban(accountGel).getAmount();
        BigDecimal actualAmountUSD = service.findAccountByIban(accountUsd).getAmount();
        BigDecimal expectedAmountGel = initialAmountGel.add(valeToExchange.multiply(rateValue).multiply(BigDecimal.valueOf(transferCount)));

        BigDecimal expectedAmountUsd = initialAmountUsd.subtract(valeToExchange.multiply(BigDecimal.valueOf(transferCount)));
        System.out.println(expectedAmountUsd);
        Assertions.assertEquals(0, actualAmountGel.compareTo(expectedAmountGel));
        Assertions.assertEquals(0, actualAmountUSD.compareTo(expectedAmountUsd));

        service.removeUser(userUsd.getId());
        service.removeUser(userGel.getId());


    }



    private void printAccountsState(String accountOneIban, String accountTwoIban, ExchangeService service){
        Account accountOne = service.findAccountByIban(accountOneIban);
        Account accountTwo = service.findAccountByIban(accountTwoIban);
        System.out.printf(
                "Accounts state:\n%s: %s\n%s: %s%n",
                accountOne.getCurrency(),
                accountOne,
                accountTwo.getCurrency(),
                accountTwo);
    }
}
