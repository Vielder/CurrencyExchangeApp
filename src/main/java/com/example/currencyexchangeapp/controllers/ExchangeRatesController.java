package com.example.currencyexchangeapp.controllers;

import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;
import com.example.currencyexchangeapp.services.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRatesController {

    private final ExchangeRatesService exchangeRatesService;

    @Autowired
    public ExchangeRatesController(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }


    @GetMapping("currencies")
    public List<Currency> getCurrencyList() {
        return exchangeRatesService.getCurrencyList();
    }
}
