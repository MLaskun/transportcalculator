package com.example.transportcalculator.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    private int productionTime;
    private int weight;

    public Products(String productName, int productionTime, int weight) {
        this.productName = productName;
        this.productionTime = productionTime;
        this.weight = weight;
    }
}
