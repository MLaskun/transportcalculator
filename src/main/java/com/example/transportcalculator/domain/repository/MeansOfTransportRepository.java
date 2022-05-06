package com.example.transportcalculator.domain.repository;

import com.example.transportcalculator.domain.entity.MeansOfTransport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeansOfTransportRepository extends JpaRepository<MeansOfTransport, Long> {
}
