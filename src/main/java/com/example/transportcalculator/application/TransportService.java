package com.example.transportcalculator.application;


import com.example.transportcalculator.domain.entity.MeansOfTransport;
import com.example.transportcalculator.domain.entity.Products;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransportService {

    public TransportDetails findCheapestDelivery(List<Products> productsList, List<MeansOfTransport> motList) {
        TransportDetails transportDetails = new TransportDetails();
        List<Products> productsListSorted = sortByWeight(productsList);
        List<MeansOfTransport> motListSorted = sortByCost(motList);
        List<Products> productsListSortedTemp = productsListSorted;
        productsListSortedTemp.forEach(product -> {
            motListSorted.forEach(mot -> {
                List<Products> transport = new ArrayList<>();
                if (product.getWeight() <= mot.getMaxWeight()) {
                    System.out.println(buildSingleTransport(product, productsListSortedTemp, transport, mot));
                }
            });
        });

        return transportDetails;
    }

    public int addAllWeights(List<Products> products) {
        int totalWeight = products.stream().mapToInt(Products::getWeight).sum();
        return totalWeight;
    }

    public List<Products> sortByWeight(List<Products> productsList) {
        productsList.sort(Comparator.comparingInt(Products::getWeight));
        Collections.reverse(productsList);
        return productsList;
    }

    public List<MeansOfTransport> sortByCost(List<MeansOfTransport> motList) {
        motList.sort(Comparator.comparingInt(MeansOfTransport::getCost));
        return motList;
    }

    private List<Products> buildSingleTransport(Products product, List<Products> productsList, List<Products> transport, MeansOfTransport mot) {

        transport.add(product);
        productsList.remove(product);
        int maxWeight = mot.getMaxWeight();
        int actualWeight = addAllWeights(transport);
        if (maxWeight > actualWeight) {
            productsList.forEach(products -> {
                if (products.getWeight() <= maxWeight - actualWeight) {
                    buildSingleTransport(products, productsList, transport, mot);
                }
            });
        }
        return transport;
    }
}