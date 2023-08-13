package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.CalculationResult;
import org.springframework.stereotype.Service;

public interface Calculator {
    CalculationResult calculateRate(double amount, String currencyFrom, String currencyTo);
}
