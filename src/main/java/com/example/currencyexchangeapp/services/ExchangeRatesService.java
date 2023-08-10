package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRatesService {
    List<ExchangeRate> getFxRatesForAllCurrencies();
    List<ExchangeRate> getFxRatesForCurrency(String type, String currency, LocalDate dateFrom, LocalDate dateTo);
    List<ExchangeRate> getFxRatesForAllCurrenciesBetweenDate(LocalDate dateFrom, LocalDate dateTo);
    List<Currency> getCurrencyList();
}
