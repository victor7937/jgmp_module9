package com.epam.victor.exchanger.repository.impl;

import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;

import java.util.List;
import java.util.Optional;

public class ExchangeCurrencyRateRepository implements CurrencyRateRepository {
    @Override
    public CurrencyRate create(CurrencyRate currencyRate) {
        return null;
    }

    @Override
    public Optional<CurrencyRate> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CurrencyRate> findAll() {
        return null;
    }
}
