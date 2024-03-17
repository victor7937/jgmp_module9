package com.epam.victor.exchanger.repository.json.converter;

import com.epam.victor.exchanger.model.CurrencyPair;
import com.fasterxml.jackson.databind.util.StdConverter;

public class StringCurrencyPairConverter extends StdConverter<String, CurrencyPair> {
    @Override
    public CurrencyPair convert(String s) {
        return CurrencyPair.of(s);
    }
}
