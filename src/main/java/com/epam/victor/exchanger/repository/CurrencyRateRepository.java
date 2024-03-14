package com.epam.victor.exchanger.repository;

import com.epam.victor.exchanger.model.CurrencyRate;

import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository {

    CurrencyRate create(CurrencyRate currencyRate);

    Optional<CurrencyRate> findById(Long id);

    List<CurrencyRate> findAll();




}
