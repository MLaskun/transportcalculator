package com.example.transportcalculator.application;


import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.MeanOfTransport;
import com.example.transportcalculator.domain.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TransportCalculator implements Calculator {

    private final List<MeanOfTransport> availableTransports;
    private List<TransportRule> calculationRules = new ArrayList<>();

    public TransportCalculator(List<MeanOfTransport> transports) {
        transports.sort(Comparator.comparingInt(MeanOfTransport::getCost));
        this.availableTransports = transports;
        this.calculationRules.add(new SingleTransportRule());
        this.calculationRules.add(new MultipleTransportRule());
    }

    @Override
    public List<ChosenTransport> calculateTransports(List<Product> products) {
        List<ChosenTransport> chosenTransports = new ArrayList<>();
        List<Product> orderedProducts = sortByWeight(products);
        int currentCost = Integer.MAX_VALUE;
        for (TransportRule transportRule : calculationRules) {
            List<ChosenTransport> transports = transportRule.calculateTransports(availableTransports, orderedProducts);
            currentCost = Integer.min(currentCost, transports.stream().mapToInt(ChosenTransport::getCost).sum());
            chosenTransports = transports;
        }
        return chosenTransports;
    }

    public List<Product> sortByWeight(List<Product> productsList) {
        productsList.sort(Comparator.comparingInt(Product::getWeight));
        Collections.reverse(productsList);
        return productsList;
    }
}