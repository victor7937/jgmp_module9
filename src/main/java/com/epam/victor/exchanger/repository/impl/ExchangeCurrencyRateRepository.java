package com.epam.victor.exchanger.repository.impl;

import com.epam.victor.exchanger.model.CurrencyPair;
import com.epam.victor.exchanger.model.CurrencyRate;
import com.epam.victor.exchanger.repository.CurrencyRateRepository;
import com.epam.victor.exchanger.repository.json.JsonFileUtil;
import com.epam.victor.exchanger.repository.json.ObjectMapperConfigurer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

public class ExchangeCurrencyRateRepository implements CurrencyRateRepository {

    private ObjectMapper objectMapper;

    private final static String CURRENCY_RATE_PATH = "C:\\Users\\Victor_Vyrostak\\Documents\\Mentoring\\Module9_Multithreading\\exchange_app_data\\CurrencyRate\\";

    public ExchangeCurrencyRateRepository() {
        this.objectMapper = ObjectMapperConfigurer.configure(new ObjectMapper());
    }


    @Override
    public void save(CurrencyRate currencyRate) {
        JsonFileUtil.writeObjectToFile(
                currencyRate,
                CURRENCY_RATE_PATH + pairToFileName(currencyRate.getCurrencyPair()),
                objectMapper,
                new TypeReference<>() {});
    }

    @Override
    public Optional<CurrencyRate> findByCurrencyPair(CurrencyPair pair) {
        return JsonFileUtil.fileByPathToObject(
                CURRENCY_RATE_PATH + pairToFileName(pair),
                objectMapper,
                new TypeReference<>() {});
    }

    @Override
    public List<CurrencyRate> findAll() {
        return JsonFileUtil.findAllFromFolder(CURRENCY_RATE_PATH, objectMapper, new TypeReference<>() {});
    }

    private String pairToFileName(CurrencyPair currencyPair){
        return currencyPair.toString().replace("/","-") + ".json";
    }
}
