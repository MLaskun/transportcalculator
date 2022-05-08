package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.MeanOfTransport;
import com.example.transportcalculator.domain.entity.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SingleTransportRule implements TransportRule {
    @Override
    public List<ChosenTransport> calculateTransports(List<MeanOfTransport> availableTransports, List<Product> products) {
        List<ChosenTransport> choosenTransports = new ArrayList<>();
        List<MeanOfTransport> availableSingleTransports = new ArrayList<>();

        availableTransports.forEach(transport -> {
            List<Product> canCarryProducts = transport.canCarryProducts(products);

            if (canCarryProducts.stream().count() == products.stream().count())
                availableSingleTransports.add(transport);
        });
        if (!availableSingleTransports.isEmpty()) {
            availableSingleTransports.sort(Comparator.comparingInt(MeanOfTransport::getCost));
            MeanOfTransport cheapestSingleTransport = availableSingleTransports.get(0);

            choosenTransports.add(new ChosenTransport(cheapestSingleTransport, products));
        }
        return choosenTransports;
    }
}
