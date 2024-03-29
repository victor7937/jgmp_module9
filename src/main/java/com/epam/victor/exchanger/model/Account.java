package com.epam.victor.exchanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String iban;

    private Currency currency;

    private BigDecimal amount;
}
