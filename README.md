# CurrencyExchangeApp

Task: exchange rate portal
 
Write a web application with such functionality:

1. Central bank exchange rates page. Exchange rates from the Bank of Lithuania are displayed here: http://www.lb.lt/webservices/FxRates/

2. After selecting a specific currency, its exchange rate history is displayed (chart or table).

3. Currency calculator. The amount is entered, the currency is selected, and the program displays the amount in foreign currency and the rate at which it was calculated.

Exchange rates must be automatically obtained every day (eg using quartz).
Use the H2 database for data storage. 
 
Backend: Java8+, maven
Fronted: any UI framework

#About Frontend

There are two versions of frontend:
- Vue.js frontend (made as a separate project);
- JavaScript frontend;

The difference is that I wanted to make a chart. It is done in js but not in the Vue.js frontend version because I could not implement it in Vue.js. So there is a simple table now instead of it. 

Vue.js frontend can be reached by URL "http://localhost:8081/"
js frontend can be reached by URL "http://localhost:8080/index.html"
