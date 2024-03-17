package com.epam.victor.exchanger.repository;

import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;

import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository {

    void save(CurrencyRate currencyRate);

    Optional<CurrencyRate> findByCurrencyPair(CurrencyPair pair);

    List<CurrencyRate> findAll();




}
