package com.example.currencyexchangeapp.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateRowMapper implements RowMapper<ExchangeRate> {

    @Override
    public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(rs.getString("ID"));
        exchangeRate.setType(rs.getString("TYPE"));
        exchangeRate.setDate(rs.getDate("DATE").toLocalDate());
        exchangeRate.setCurrency1(rs.getString("CURRENCY1"));
        exchangeRate.setAmount1(rs.getDouble("AMOUNT1"));
        exchangeRate.setCurrency2(rs.getString("CURRENCY2"));
        exchangeRate.setAmount2(rs.getDouble("AMOUNT2"));
        return exchangeRate;
    }
}