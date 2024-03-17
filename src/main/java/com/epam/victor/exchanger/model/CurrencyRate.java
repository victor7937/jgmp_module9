package com.epam.victor.exchanger.model;

import com.epam.victor.exchanger.repository.json.converter.CurrencyPairStringConverter;
import com.epam.victor.exchanger.repository.json.converter.StringCurrencyPairConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    @JsonSerialize(converter = CurrencyPairStringConverter.class)
    @JsonDeserialize(converter = StringCurrencyPairConverter.class)
    private CurrencyPair currencyPair;

    private BigDecimal rate;

}
