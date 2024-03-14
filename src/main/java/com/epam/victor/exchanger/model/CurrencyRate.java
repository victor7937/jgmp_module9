package com.epam.victor.exchanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    private Long id;

    private CurrencyPair currencyPair;

    private BigDecimal buy;

    private BigDecimal sell;
}
