package com.example.transportcalculator.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChosenTransport {
    public MeanOfTransport transport;
    public List<Product> products;

    public ChosenTransport() {
        this.products = new ArrayList<>();
    }

    public ChosenTransport(MeanOfTransport transport, List<Product> products) {
        this.transport = transport;
        this.products = products;
    }

    public int getCost() {
        return this.transport.getCost();
    }
}
