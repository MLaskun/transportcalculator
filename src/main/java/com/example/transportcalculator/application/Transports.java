package com.example.transportcalculator.application;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Transports {
    private String mot;
    private int cost;
    private int deliveryDays;
    private int weight;
    private List<String> products;

    public Transports(String mot, int cost, int deliveryDays, int weight, List<String> products) {
        this.mot = mot;
        this.cost = cost;
        this.deliveryDays = deliveryDays;
        this.weight = weight;
        this.products = products;
    }
}
