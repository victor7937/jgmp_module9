package com.epam.victor.exchanger.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class CurrencyPair {
    private static final String C_PAIR_PATTERN = "([A-Z]{3})/([A-Z]{3})";

    private Currency base;

    private Currency quote;


    @Override
    public String toString() {
        return base.getCurrencyCode() + "/" + quote.getCurrencyCode();
    }

    public static CurrencyPair of(String pairString) {
        Pattern pattern = Pattern.compile(C_PAIR_PATTERN);
        Matcher matcher = pattern.matcher(pairString.toUpperCase(Locale.ENGLISH));
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid currency pair: " + pairString);
        }
        Currency base = Currency.getInstance(matcher.group(1));
        Currency counter = Currency.getInstance(matcher.group(2));
        return new CurrencyPair(base, counter);
    }


}
