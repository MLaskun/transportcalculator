package com.example.transportcalculator.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransportDetails {
    private int totalCost;
    private int totalWeight;
    private List<Transport> transports;

    public TransportDetails(int totalCost, int totalWeight, List<Transport> transports) {
        this.totalCost = totalCost;
        this.totalWeight = totalWeight;
        this.transports = transports;
    }
}
