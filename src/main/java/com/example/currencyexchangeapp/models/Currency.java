package com.example.currencyexchangeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
@Table(name = "CURRENCY_LIST")
public class Currency {
    @Id
    @Column
    private int id;
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
