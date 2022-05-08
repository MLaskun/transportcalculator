package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.MeanOfTransport;
import com.example.transportcalculator.domain.entity.Product;
import com.example.transportcalculator.domain.entity.TransportWithCostWrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultipleTransportRule implements TransportRule {
    @Override
    public List<ChosenTransport> calculateTransports(List<MeanOfTransport> availableTransports, List<Product> products) {
        List<ChosenTransport> result = new ArrayList<>();
        int minSingleWeight = products.get(products.size() - 1).getWeight();

        List<MeanOfTransport> transports = new ArrayList<>();
        availableTransports.forEach(meanOfTransport -> {
            if (meanOfTransport.getMaxWeight() >= minSingleWeight)
                transports.add(meanOfTransport);
        });

        if (transports.isEmpty())
            return result;

        List<TransportWithCostWrapper<Integer, List<ChosenTransport>>> variations = GenerateVariations(transports, products);

        if (variations.isEmpty())
            return result;

        variations.sort(Comparator.comparingInt(TransportWithCostWrapper::getTotalCost));
        result.addAll(variations.get(0).getTransports());
        return result;
    }

    private List<TransportWithCostWrapper<Integer, List<ChosenTransport>>> GenerateVariations(List<MeanOfTransport> transports, List<Product> products) {
        List<TransportWithCostWrapper<Integer, List<ChosenTransport>>> variations = new ArrayList<>();
        List<Product> productsTemp = new ArrayList<>();
        productsTemp.addAll(products);
        List<ChosenTransport> sameTransportsPack = packProductsToSameTypeTransport(transports, productsTemp, new ArrayList<>());
        variations.add(new TransportWithCostWrapper(sameTransportsPack.stream().mapToInt(ChosenTransport::getCost).sum(), sameTransportsPack));
        return variations;
    }

    private List<ChosenTransport> packProductsToSameTypeTransport(List<MeanOfTransport> transports, List<Product> products, List<ChosenTransport> currentChosenTransport) {
        List<ChosenTransport> result = currentChosenTransport;
        ChosenTransport currentTransport = new ChosenTransport();
        List<Product> currentProductList = new ArrayList<>();
        List<Product> productsTemp = new ArrayList<>();
        productsTemp.addAll(products);
        if (!products.isEmpty()) {
            for (MeanOfTransport mot : transports) {
                currentTransport.setTransport(mot);
                int availableWeight = mot.getMaxWeight();
                for (Product product : products) {
                    if (availableWeight >= product.getWeight() && availableWeight - product.getWeight() >= 0 && (!currentProductList.isEmpty() || products.get(0) == product)) {
                        currentProductList.add(product);
                        productsTemp.remove(product);
                        availableWeight -= product.getWeight();
                    }
                    currentTransport.setProducts(currentProductList);
                }
                if (!currentProductList.isEmpty()) {
                    result.add(currentTransport);
                    break;
                }
            }
            packProductsToSameTypeTransport(transports, productsTemp, result);
        }
        return result;
    }

}