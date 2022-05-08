package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.MeanOfTransport;
import com.example.transportcalculator.domain.entity.Product;
import com.example.transportcalculator.domain.entity.TransportWithCostWrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultipleTransportRule implements TransportRule{
    @Override
    public List<ChosenTransport> calculateTransports(List<MeanOfTransport> availableTransports, List<Product> products) {
        List<ChosenTransport> result = new ArrayList<>();
        int weightSum = products.stream().mapToInt(Product::getWeight).sum();
        int minSingleWeight = products.get(products.size()-1).getWeight();

        //TODO: modify the list somehow
        List<MeanOfTransport> transports = availableTransports;

        if (transports.isEmpty())
            return result;

        List<TransportWithCostWrapper<Integer, ChosenTransport>> variations = GenerateVariations(transports, products);

        if (variations.isEmpty())
            return result;

        variations.sort(Comparator.comparingInt(TransportWithCostWrapper::getTotalCost));
        result.addAll(variations.get(0).getTransports());
        return result;
    }

    private List<TransportWithCostWrapper<Integer, ChosenTransport>> GenerateVariations(List<MeanOfTransport> transports, List<Product> products) {
        //TODO
        List<TransportWithCostWrapper<Integer,ChosenTransport>> result = new ArrayList<>();
        List<ChosenTransport> chosenTransports = new ArrayList<>();
        chosenTransports.add(new ChosenTransport(transports.get(0), products));
        result.add(new TransportWithCostWrapper(1234, chosenTransports));
        return result;
    }

}
