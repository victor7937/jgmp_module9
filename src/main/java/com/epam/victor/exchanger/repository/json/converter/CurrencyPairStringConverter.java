package com.epam.victor.exchanger.repository.json.converter;

import com.epam.victor.exchanger.model.CurrencyPair;
import com.fasterxml.jackson.databind.util.StdConverter;

public class CurrencyPairStringConverter extends StdConverter<CurrencyPair, String> {
    @Override
    public String convert(CurrencyPair pair) {
        return pair.toString();
    }
}
