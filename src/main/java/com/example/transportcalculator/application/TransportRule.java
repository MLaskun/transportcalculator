package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.MeanOfTransport;
import com.example.transportcalculator.domain.entity.Product;

import java.util.List;

public interface TransportRule {
    List<ChosenTransport> calculateTransports(List<MeanOfTransport> availableTransports, List<Product> products);
}
