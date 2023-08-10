package com.example.currencyexchangeapp.services;

import com.example.currencyexchangeapp.models.Currency;
import com.example.currencyexchangeapp.models.ExchangeRate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService{
    private JdbcTemplate jdbcTemplate;
    public ExchangeRatesServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ExchangeRate> getFxRatesForAllCurrencies() {
        String baseUrl = "https://www.lb.lt//webservices/FxRates/FxRates.asmx/getFxRatesForCurrency";
        String dateFrom = "1993-12-30";
//        String dateFrom = "2023-08-08";
        String dateTo = LocalDate.now().toString();
        String url = baseUrl + "?tp=&ccy=" + "&dtFrom=" + dateFrom + "&dtTo=" + dateTo;
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);
        List<ExchangeRate> rates = parseExchangeRatesXml(xmlResponse);
        for (ExchangeRate rate : rates
        ) {
            String sql = "INSERT INTO EXCHANGE_RATES (ID, TYPE, DATE, CURRENCY1, AMOUNT1, CURRENCY2, AMOUNT2)\n" +
                    "VALUES (" +
                    "'"+rate.getId() +"',"+
                    "'"+rate.getType() +"',"+
                    "'"+rate.getDate() +"',"+
                    "'"+rate.getCurrency1() +"',"+
                    "'"+rate.getAmount1() +"',"+
                    "'"+rate.getCurrency2() +"',"+
                    "'"+rate.getAmount2() +
                    "');";
            jdbcTemplate.execute(sql);
        }

        return rates;
    }

    @Override
    public List<ExchangeRate> getFxRatesForCurrency(String type, String currency, LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }

    @Override
    public List<ExchangeRate> getFxRatesForAllCurrenciesBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        String baseUrl = "https://www.lb.lt//webservices/FxRates/FxRates.asmx/getFxRatesForCurrency";
        String url = baseUrl + "?tp=&ccy=" + "&dtFrom=" + dateFrom.toString() + "&dtTo=" + dateTo.toString();
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);
        List<ExchangeRate> rates = parseExchangeRatesXml(xmlResponse);
        for (ExchangeRate rate : rates
        ) {
            String sql = "INSERT INTO EXCHANGE_RATES (ID, TYPE, DATE, CURRENCY1, AMOUNT1, CURRENCY2, AMOUNT2)\n" +
                    "VALUES (" +
                    "'"+rate.getId() +"',"+
                    "'"+rate.getType() +"',"+
                    "'"+rate.getDate() +"',"+
                    "'"+rate.getCurrency1() +"',"+
                    "'"+rate.getAmount1() +"',"+
                    "'"+rate.getCurrency2() +"',"+
                    "'"+rate.getAmount2() +
                    "');";
            jdbcTemplate.execute(sql);
        }

        return rates;
    }

    @Override
    public List<Currency> getCurrencyList() {
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject("https://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrencyList?", String.class);
        List<Currency> currencies = parseCurrencyListXml(xmlResponse);
        for (Currency currency : currencies
        ) {
            String sql = "INSERT INTO CURRENCY_LIST (ID, CURRENCY, NAME_LT, NAME_EN, CURR_ID, CCY_MNR_UNTS)\n" +
                    "VALUES (" +
                    "'"+currency.getId() +"',"+
                    "'"+currency.getCurrency() +"',"+
                    "'"+currency.getNameLT() +"',"+
                    "'"+currency.getNameEN() +"',"+
                    "'"+currency.getCurrID() +"',"+
                    "'"+currency.getCcyMnrUnts() +
                    "');";
            jdbcTemplate.execute(sql);
        }
        return currencies;
    }

    private List<ExchangeRate> parseExchangeRatesXml(String xml) {
        List<ExchangeRate> rates = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(new StringReader(xml));
            Document document = builder.parse(source);

            NodeList fxRates = document.getElementsByTagName("FxRate");
            for (int i = 0; i < fxRates.getLength(); i++) {
                Element fxRateElement = (Element) fxRates.item(i);
                String type = fxRateElement.getElementsByTagName("Tp").item(0).getTextContent();
                LocalDate date = LocalDate.parse(fxRateElement.getElementsByTagName("Dt").item(0).getTextContent());

                NodeList ccyAmtList = fxRateElement.getElementsByTagName("CcyAmt");
                Element ccyAmt1 = (Element) ccyAmtList.item(0);
                String currency1 = ccyAmt1.getElementsByTagName("Ccy").item(0).getTextContent();
                double amount1 = Double.parseDouble(ccyAmt1.getElementsByTagName("Amt").item(0).getTextContent());

                Element ccyAmt2 = (Element) ccyAmtList.item(1);
                String currency2 = ccyAmt2.getElementsByTagName("Ccy").item(0).getTextContent();
                double amount2 = Double.parseDouble(ccyAmt2.getElementsByTagName("Amt").item(0).getTextContent());

                ExchangeRate rate = new ExchangeRate(type, date, currency1, amount1, currency2, amount2);
                rates.add(rate);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return rates;
    }

    private List<Currency> parseCurrencyListXml(String xml) {
        List<Currency> currencies = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(new StringReader(xml));
            Document document = builder.parse(source);

            NodeList CcyTbl = document.getElementsByTagName("CcyNtry");
            for (int i = 0; i < CcyTbl .getLength(); i++) {
                Element CcyNtryElement = (Element) CcyTbl.item(i);
                String ccy = CcyNtryElement.getElementsByTagName("Ccy").item(0).getTextContent();
                String nameLT = CcyNtryElement.getElementsByTagName("CcyNm").item(0).getTextContent();
                String nameEN = CcyNtryElement.getElementsByTagName("CcyNm").item(1).getTextContent();
                String currID = CcyNtryElement.getElementsByTagName("CcyNbr").item(0).getTextContent();
                int ccyMnrUnts = Integer.parseInt(CcyNtryElement.getElementsByTagName("CcyMnrUnts").item(0).getTextContent());
                Currency currency = new Currency(2 ,ccy, nameLT, nameEN, currID, ccyMnrUnts);
                currencies.add(currency);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return currencies;
    }
}
