package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface DBService {
    void insertExchangeRates(ExchangeRate exchangeRate);
    void insertCurrencyList(Currency currency);
    LocalDate getLastDateFromExchangeRatesTable();
    ExchangeRate getTodayExchangeRate(String currency);
    List<ExchangeRate> getExchangeRateBetweenDates(String currency, LocalDate dateFrom, LocalDate dateTo);
    List<Currency> getCurrencyList();
}
