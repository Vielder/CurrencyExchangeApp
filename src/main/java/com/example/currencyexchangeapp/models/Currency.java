package com.example.currencyexchangeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
@Table(name = "CURRENCY_LIST")
public class Currency {
    @org.springframework.data.annotation.Id
    @Id
    @GeneratedValue
    @Column
    private Integer id;
    @Column
    private String currency;
    @Column
    private String nameLT;
    @Column
    private String nameEN;
    @Column
    private String currID;
    @Column
    private int CcyMnrUnts; // Ammount of numbers after "."

    public Currency() {

    }
}
