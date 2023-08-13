package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.CurrencyExchangeAppApplication;
import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class InitialDBUpdateImpl implements InitialDBUpdate{
    @Autowired
    private APIService apiService;
    @Autowired
    private DBService dbService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private boolean isQuartzSchedulerStarted = false;

    @Override
    public void start() {
        if (!isQuartzSchedulerStarted) {
            System.out.println("This process may take about 2-5 minutes if it's the first start of the application and there is no data in db.");
            LocalDate lastDateInTable = dbService.getLastDateFromExchangeRatesTable();
            List<ExchangeRate> exchangeRateList;
            if (lastDateInTable == null){
                System.out.println("No data in the table. Downloading all the data from API...");
                exchangeRateList = apiService.getFxRatesForAllCurrencies();
                System.out.println("Data received. Uploading to database...");

                for (ExchangeRate rate: exchangeRateList
                ) {
                    dbService.insertExchangeRates(rate);
                }
            } else if (lastDateInTable.isEqual(LocalDate.now())) {
                System.out.println("All data already in the table.");
            } else if (lastDateInTable.isBefore(LocalDate.now())) {
                System.out.println("Adding missing data from API to the table. Last date in the table: "+ lastDateInTable);
                exchangeRateList = apiService.getFxRatesForAllCurrenciesBetweenDate(lastDateInTable.plusDays(1), LocalDate.now());
                for (ExchangeRate rate: exchangeRateList
                     ) {
                        dbService.insertExchangeRates(rate);
                }
            } else {
                System.out.println("Something went wrong!");
            }
            String sql = "SELECT COUNT(1) FROM currency_list";
            List<Currency> currencyList;
            if (Objects.equals(jdbcTemplate.queryForObject(sql, int.class), 0)){
                currencyList = apiService.getCurrencyList();
                for (Currency currency: currencyList
                     ) {
                        dbService.insertCurrencyList(currency);
                }
            }
            System.out.println("All data up to date.");

            isQuartzSchedulerStarted = true;

        }
    }
}
