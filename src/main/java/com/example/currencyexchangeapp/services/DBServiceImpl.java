package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.CurrencyRowMapper;
import com.example.currencyexchangeapp.models.ExchangeRate;
import com.example.currencyexchangeapp.models.ExchangeRateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DBServiceImpl implements DBService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertExchangeRates(ExchangeRate exchangeRate) {
        String sql = "INSERT INTO EXCHANGE_RATES (ID, TYPE, DATE, CURRENCY1, AMOUNT1, CURRENCY2, AMOUNT2) VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(
                sql,
                exchangeRate.getId(),
                exchangeRate.getType(),
                exchangeRate.getDate(),
                exchangeRate.getCurrency1(),
                exchangeRate.getAmount1(),
                exchangeRate.getCurrency2(),
                exchangeRate.getAmount2()
        );
    }

    @Override
    public void insertCurrencyList(Currency currency) {
        String sql = "INSERT INTO CURRENCY_LIST (ID, CURRENCY, NAME_LT, NAME_EN, CURR_ID, CCY_MNR_UNTS) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                currency.getId(),
                currency.getCurrency(),
                currency.getNameLT(),
                currency.getNameEN(),
                currency.getCurrID(),
                currency.getCcyMnrUnts()
        );
    }


    @Override
    public LocalDate getLastDateFromExchangeRatesTable() {
        String sql = "SELECT MAX(date) FROM exchange_rates";
        return jdbcTemplate.queryForObject(sql, LocalDate.class);
    }

    @Override
    public ExchangeRate getTodayExchangeRate(String currency) {
        String sql = "SELECT * FROM exchange_rates WHERE CURRENCY2 = ? AND DATE = ?";
        LocalDate currentDate = LocalDate.now();

        ExchangeRateRowMapper rowMapper = new ExchangeRateRowMapper();

        return jdbcTemplate.queryForObject(sql, rowMapper, currency, currentDate);
    }

    @Override
    public List<ExchangeRate> getExchangeRateBetweenDates(String currency, LocalDate dateFrom, LocalDate dateTo) {
        String sql = "SELECT * FROM exchange_rates WHERE CURRENCY2 = ? AND DATE BETWEEN ? AND ?";

        ExchangeRateRowMapper rowMapper = new ExchangeRateRowMapper();

        return jdbcTemplate.query(sql, rowMapper, currency, dateFrom, dateTo);
    }

    @Override
    public List<Currency> getCurrencyList() {
        String sql = "SELECT * FROM currency_list";

        CurrencyRowMapper rowMapper = new CurrencyRowMapper();

        return jdbcTemplate.query(sql, rowMapper);
    }
}
