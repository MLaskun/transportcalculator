package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.Product;
import com.example.transportcalculator.domain.entity.TransportDetails;

import java.util.List;

public interface Calculator {
    TransportDetails calculateTransports(List<Product> products);
}
