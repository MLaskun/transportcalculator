package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.Product;

import java.util.List;

public interface Calculator {
    List<ChosenTransport> calculateTransports(List<Product> products);
}
