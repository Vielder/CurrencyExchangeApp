package com.example.currencyexchangeapp.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRowMapper implements RowMapper<Currency> {

    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        Currency currency = new Currency();
        currency.setId(rs.getInt("ID"));
        currency.setCurrency(rs.getString("CURRENCY"));
        currency.setNameLT(rs.getString("NAME_LT"));
        currency.setNameEN(rs.getString("NAME_EN"));
        currency.setCurrID(rs.getString("CURR_ID"));
        currency.setCcyMnrUnts(rs.getInt("CCY_MNR_UNTS"));
        return currency;
    }
}
