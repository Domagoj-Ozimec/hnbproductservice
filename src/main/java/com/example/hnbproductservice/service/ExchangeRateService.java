package com.example.hnbproductservice.service;

public interface ExchangeRateService {

    double getNewExchangeRateIfNoneExists(String currency);

    double getExchangeRate(String currency);
}
