package com.example.transportcalculator.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransportWithCostWrapper<I extends Number, M> {
    private int totalCost;
    private List<ChosenTransport> transports;

    public TransportWithCostWrapper(int totalCost, List<ChosenTransport> transports) {
        this.totalCost = totalCost;
        this.transports = transports;
    }
}
