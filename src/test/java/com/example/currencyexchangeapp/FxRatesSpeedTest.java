package com.example.currencyexchangeapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FxRatesSpeedTest {



    // Test shown that initial DB update will take 2min 3sec.
    @Test
    public void testFxRatesRetrievalSpeed() {
        String baseUrl = "https://www.lb.lt//webservices/FxRates/FxRates.asmx/getFxRatesForCurrency";
        String dateFrom = "1993-12-30";
        String dateTo = LocalDate.now().toString();
        String url = baseUrl + "?tp=&ccy=" + "&dtFrom=" + dateFrom + "&dtTo=" + dateTo;
//        String url = "https://www.lb.lt//webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=&ccy=&dtFrom=1993-12-30&dtTo=2023-08-09";

        RestTemplate restTemplate = new RestTemplate();
        long startTime = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        int statusCode = response.getStatusCodeValue();
        String responseBody = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() != null && !response.getBody().isEmpty());

        System.out.println("Response time: " + responseTime + " ms");
    }
}
