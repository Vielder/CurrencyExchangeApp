package com.example.currencyexchangeapp.util;

import com.example.currencyexchangeapp.models.ExchangeRate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomIdGenerator implements IdentifierGenerator{
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        ExchangeRate rate = (ExchangeRate) object;
        String id = rate.getCurrency2() + "_" + rate.getDate();
        return id;
    }
}
