package com.example.transportcalculator.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeanOfTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int cost;
    private int deliveryTime;
    private int minWeight;
    private int maxWeight;

    public MeanOfTransport(String name, int cost, int deliveryTime, int minWeight, int maxWeight) {
        this.name = name;
        this.cost = cost;
        this.deliveryTime = deliveryTime;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public boolean canTransportProduct(Product product) {
        return minWeight < product.getWeight() && maxWeight - product.getWeight() > 0;
    }

    public List<Product> canCarryProducts(List<Product> products) {
        List<Product> result = new ArrayList<>();
        int weightSum = 0;

        for(Product product : products) {
            weightSum += product.getWeight();
            if(weightSum < maxWeight)
                result.add(product);
        }
        return result;
    }
}
