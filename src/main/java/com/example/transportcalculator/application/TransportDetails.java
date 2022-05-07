package com.example.transportcalculator.application;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransportDetails {
    private int totalCost;
    private int totalWeight;
    private List<Transports> transports;

    public TransportDetails(int totalCost, int totalWeight, List<Transports> transports) {
        this.totalCost = totalCost;
        this.totalWeight = totalWeight;
        this.transports = transports;
    }
}
