package com.example.currencyexchangeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculationResult {
    private double amount;
    private double rate;
}
