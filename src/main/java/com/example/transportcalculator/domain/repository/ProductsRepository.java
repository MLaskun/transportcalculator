package com.example.transportcalculator.domain.repository;

import com.example.transportcalculator.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}
