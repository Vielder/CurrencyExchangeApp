package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.CalculationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CalculatorImpl implements Calculator {

    @Autowired
    private DBService dbService;

    @Override
    public CalculationResult calculateRate(double amount, String currencyFrom, String currencyTo) {
        double rateFrom;
        double rateTo;
        if (Objects.equals(currencyFrom, "EUR")){
            rateFrom = 1;
        } else {
            rateFrom = dbService.getTodayExchangeRate(currencyFrom).getAmount2();
        }
        if (Objects.equals(currencyTo, "EUR")){
            rateTo = 1;
        } else {
            rateTo = dbService.getTodayExchangeRate(currencyTo).getAmount2();
        }
        double resultRate = rateTo / rateFrom;
        double resultAmount = amount * resultRate;

        CalculationResult result = new CalculationResult(resultAmount, resultRate);
        return result;
    }
}
