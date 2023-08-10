package com.example.currencyexchangeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "EXCHANGE_RATES")
public class ExchangeRate {

    @Id
    @Column
    private String id;
    @Column
    private String type;
    @Column
    private LocalDate date;
    @Column
    private String currency1;
    @Column
    private double amount1;
    @Column
    private String currency2;
    @Column
    private double amount2;

    public ExchangeRate(String type, LocalDate date, String currency1, double amount1, String currency2, double amount2) {
        this.id = currency2 +"_"+ date;
        this.type = type;
        this.date = date;
        this.currency1 = currency1;
        this.amount1 = amount1;
        this.currency2 = currency2;
        this.amount2 = amount2;
    }

    public ExchangeRate() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
