CREATE TABLE IF NOT EXISTS currency_list (
    id INTEGER auto_increment,
    currency VARCHAR2 PRIMARY KEY,
    name_lt VARCHAR2,
    name_EN VARCHAR2,
    curr_ID VARCHAR2,
    ccy_Mnr_Unts INTEGER
);

CREATE TABLE IF NOT EXISTS exchange_rates (
    id VARCHAR2 PRIMARY KEY,
    type VARCHAR2,
    date DATE,
    currency1 VARCHAR2,
    amount1 DOUBLE,
    currency2 VARCHAR2,
    amount2 DOUBLE
);
