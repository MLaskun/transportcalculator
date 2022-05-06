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
public class MeansOfTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int cost;
    private int deliveryTime;
    private int minWeight;
    private int maxWeight;

    public MeansOfTransport(String name, int cost, int deliveryTime, int minWeight, int maxWeight) {
        this.name = name;
        this.cost = cost;
        this.deliveryTime = deliveryTime;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }
}
