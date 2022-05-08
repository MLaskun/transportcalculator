package com.example.transportcalculator.application;

import com.example.transportcalculator.domain.entity.MeansOfTransport;
import com.example.transportcalculator.domain.entity.Products;
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
        List<Products> products = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParser parser = new CsvParser(setParser());
        List<Record> records = parser.parseAllRecords(inputStream);
        records.forEach(record -> {
            Products product = new Products();
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
        List<MeansOfTransport> meansOfTransport = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParser parser = new CsvParser(setParser());
        List<Record> records = parser.parseAllRecords(inputStream);
        records.forEach(record -> {
            MeansOfTransport meanOfTransport = new MeansOfTransport();
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
    public ResponseEntity<List<Products>> test() {
        TransportService transportService = new TransportService();
        List<MeansOfTransport> meansOfTransport = meansOfTransportRepository.findAll();
        List<String> productsList = new ArrayList<>();
        productsList.add("Product001");
        productsList.add("Product201");
        productsList.add("Product011");
        productsList.add("Product321");
        productsList.add("Product402");
        List<Products> products = mapProducts(productsList);
        System.out.println(transportService.findCheapestDelivery(products, meansOfTransport));
//        System.out.println(transportService.sortByWeight(products));
        return new ResponseEntity(products, HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<Products> test2() {
        Products product = productsRepository.findByProductName("Product002");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private List<Products> mapProducts(List<String> productsList) {
        List<Products> products = new ArrayList<>();
        productsList.forEach(product -> {
            products.add(productsRepository.findByProductName(product));
        });
        return products;
    }
}
