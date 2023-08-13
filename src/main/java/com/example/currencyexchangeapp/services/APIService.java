package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface APIService {
    List<ExchangeRate> getFxRatesForAllCurrencies();
    List<ExchangeRate> getFxRatesForAllCurrenciesBetweenDate(LocalDate dateFrom, LocalDate dateTo);
    List<Currency> getCurrencyList();
}
