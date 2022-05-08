package com.example.transportcalculator.application;


import com.example.transportcalculator.domain.entity.*;
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
    public TransportDetails calculateTransports(List<Product> products) {
        List<ChosenTransport> chosenTransports = new ArrayList<>();
        List<Product> orderedProducts = sortByWeight(products);
        int currentCost = Integer.MAX_VALUE;
        for (TransportRule transportRule : calculationRules) {
            List<ChosenTransport> transports = transportRule.calculateTransports(availableTransports, orderedProducts);
            currentCost = Integer.min(currentCost, transports.stream().mapToInt(ChosenTransport::getCost).sum());
            chosenTransports = transports;
        }
        List<Transport> transports = new ArrayList<>();
        chosenTransports.forEach(t -> {
            Transport transport = new Transport(t.getTransport().getName(),
                    t.getTransport().getCost(),
                    calculateDeliveryDays(t),
                    calculateTransportWeight(t),
                    getProductsNames(t));
            transports.add(transport);
        });
        TransportDetails result = new TransportDetails(calculateTotalCost(chosenTransports), calculateTotalWeight(chosenTransports), transports);
        return result;
    }

    private List<Product> sortByWeight(List<Product> productsList) {
        productsList.sort(Comparator.comparingInt(Product::getWeight));
        Collections.reverse(productsList);
        return productsList;
    }

    private List<String> getProductsNames(ChosenTransport transport) {
        List<String> result = new ArrayList<>();
        for (Product product : transport.getProducts()) {
            result.add(product.getProductName());
        }
        return result;
    }

    private int calculateDeliveryDays(ChosenTransport transport) {
        int result = 0;
        for (Product product : transport.getProducts()) {
            if (product.getProductionTime() > result) {
                result = product.getProductionTime();
            }
        }
        result += transport.getTransport().getDeliveryTime();
        return result;
    }

    private int calculateTotalWeight(List<ChosenTransport> transports) {
        int result = 0;
        for (ChosenTransport chosenTransport : transports) {
            for (Product product : chosenTransport.getProducts()) {
                result += product.getWeight();
            }
        }
        return result;
    }

    private int calculateTransportWeight(ChosenTransport transport) {
        int result = 0;
        for (Product product : transport.getProducts()) {
            result += product.getWeight();
        }
        return result;
    }

    private int calculateTotalCost(List<ChosenTransport> transports) {
        int result = 0;
        for (ChosenTransport chosenTransport : transports) {
            result += chosenTransport.getTransport().getCost();
        }
        return result;
    }
}