package com.example.transportcalculator.application;


import com.example.transportcalculator.domain.entity.Products;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {

    public TransportDetails findCheapestDelivery() {
        TransportDetails transportDetails = new TransportDetails();
        return transportDetails;
    }

    public int addAllWeights(List<Products> products) {
        int totalWeight = products.stream().mapToInt(Products::getWeight).sum();
        return totalWeight;
    }


}