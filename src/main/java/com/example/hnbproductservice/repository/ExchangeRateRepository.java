package com.example.hnbproductservice.repository;

import com.example.hnbproductservice.model.ExchangeRateResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateResponse, String> {
    Optional<ExchangeRateResponse> findByValutaAndDatumPrimjene(String valuta, String datumPrimjene);
}
