package com.example.currencyexchangeapp;

import com.example.currencyexchangeapp.services.ExchangeRatesService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CurrencyExchangeAppApplication {

    private final ExchangeRatesService exchangeRatesService;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeAppApplication.class);
    private boolean isQuartzSchedulerStarted = false;

    public CurrencyExchangeAppApplication(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeAppApplication.class, args);

    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!isQuartzSchedulerStarted) {
            logger.info("Quartz Scheduler started, triggering method...");
            // Вызывайте метод здесь
            exchangeRatesService.getFxRatesForAllCurrencies();
            isQuartzSchedulerStarted = true;
        }
    }

}
