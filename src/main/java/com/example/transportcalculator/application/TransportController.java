package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.ChosenTransport;
import com.example.transportcalculator.domain.entity.MeanOfTransport;
import com.example.transportcalculator.domain.entity.Product;
import com.example.transportcalculator.domain.repository.MeansOfTransportRepository;
import com.example.transportcalculator.domain.repository.ProductsRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TransportController {

    private final ProductsRepository productsRepository;

    private final MeansOfTransportRepository meansOfTransportRepository;

    public TransportController(ProductsRepository productsRepository, MeansOfTransportRepository meansOfTransportRepository) {
        this.productsRepository = productsRepository;
        this.meansOfTransportRepository = meansOfTransportRepository;
    }

    private CsvParserSettings setParser() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.getFormat().setDelimiter(";");
        return settings;
    }

    @PostMapping("/products")
    public ResponseEntity uploadProducts(@RequestParam("file") MultipartFile file) throws Exception {
        List<Product> products = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParser parser = new CsvParser(setParser());
        List<Record> records = parser.parseAllRecords(inputStream);
        records.forEach(record -> {
            Product product = new Product();
            product.setProductName(record.getString("Product Name"));
            product.setProductionTime(Integer.parseInt(record.getString("Production Time")));
            product.setWeight(Integer.parseInt(record.getString("Weight")));
            products.add(product);
        });
        productsRepository.saveAll(products);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/means")
    public ResponseEntity uploadMeansOfTransport(@RequestParam("file") MultipartFile file) throws Exception {
        List<MeanOfTransport> meansOfTransport = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParser parser = new CsvParser(setParser());
        List<Record> records = parser.parseAllRecords(inputStream);
        records.forEach(record -> {
            MeanOfTransport meanOfTransport = new MeanOfTransport();
            meanOfTransport.setName(record.getString("Means of transport"));
            meanOfTransport.setCost(Integer.parseInt(record.getString("Cost")));
            meanOfTransport.setDeliveryTime(Integer.parseInt(record.getString("Delivery Time")));
            meanOfTransport.setMinWeight(Integer.parseInt(record.getString("Min weight")));
            meanOfTransport.setMaxWeight(Integer.parseInt(record.getString("Max weight")));
            meansOfTransport.add(meanOfTransport);
        });
        meansOfTransportRepository.saveAll(meansOfTransport);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/flush")
    public void flushDatabase() {
        meansOfTransportRepository.deleteAll();
        productsRepository.deleteAll();
    }

    @GetMapping("/test")
    public ResponseEntity<List<ChosenTransport>> test() {
        List<MeanOfTransport> mot = meansOfTransportRepository.findAll();
        List<String> productsList = new ArrayList<>();
        productsList.add("Product001");
        productsList.add("Product201");
        productsList.add("Product011");
        productsList.add("Product321");
        productsList.add("Product402");
        productsList.add("Product452");
        productsList.add("Product502");
        productsList.add("Product672");
        productsList.add("Product710");
        List<Product> products = mapProducts(productsList);
        TransportCalculator transportCalculator = new TransportCalculator(mot);
        List<ChosenTransport> chosenTransports = transportCalculator.calculateTransports(products);
        return new ResponseEntity(chosenTransports, HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<Product> test2() {
        Product product = productsRepository.findByProductName("Product002");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private List<Product> mapProducts(List<String> productsList) {
        List<Product> products = new ArrayList<>();
        productsList.forEach(product -> {
            products.add(productsRepository.findByProductName(product));
        });
        return products;
    }
}
