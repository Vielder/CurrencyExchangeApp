package com.example.currencyexchangeapp.controllers;

import com.example.currencyexchangeapp.models.CalculationResult;
import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;
import com.example.currencyexchangeapp.services.APIService;
import com.example.currencyexchangeapp.services.Calculator;
import com.example.currencyexchangeapp.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange-rates")
@CrossOrigin("http://localhost:8081/")
public class ExchangeRatesController {
    @Autowired
    private final DBService dbService;
    @Autowired
    private final Calculator calculator;

    @GetMapping("currencies")
    public List<Currency> getCurrencyList() {
        return dbService.getCurrencyList();
    }

    @GetMapping("history")
    public ResponseEntity getHistoryBetweenDates(@RequestParam String currency, LocalDate dateFrom, LocalDate dateTo){
        try {
            List<ExchangeRate> exchangeRates = dbService.getExchangeRateBetweenDates(currency, dateFrom, dateTo);
            return ResponseEntity.ok(exchangeRates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("today-rate")
    public ResponseEntity getTodayExchangeRate(@RequestParam String currency){
        try {
            ExchangeRate exchangeRate = dbService.getTodayExchangeRate(currency);
            return ResponseEntity.ok(exchangeRate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("calculate")
    public ResponseEntity calculateRate(@RequestParam Double amount, String currencyFrom, String currencyTo){
        try {
            CalculationResult result = calculator.calculateRate(amount, currencyFrom, currencyTo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
