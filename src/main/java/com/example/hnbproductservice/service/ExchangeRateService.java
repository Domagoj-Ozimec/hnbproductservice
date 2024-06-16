package com.example.hnbproductservice.service;

import com.example.hnbproductservice.model.ExchangeRateResponse;
import com.example.hnbproductservice.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    private static final String HNB_API_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=";

    public double getNewExchangeRateIfNoneExists(String currency) {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateResponse[] response = restTemplate.getForObject(HNB_API_URL + currency, ExchangeRateResponse[].class);

        if (response != null && response.length > 0) {
            exchangeRateRepository.save(response[0]);
            return Double.parseDouble(response[0].getSrednjiTecaj().replace(",", "."));
        } else {
            throw new RuntimeException("Unable to fetch exchange rate from HNB API for currency: " + currency);
        }
    }

    public double getExchangeRate(String currency) {
        Optional<ExchangeRateResponse> exchangeRateResponse = exchangeRateRepository.findByValutaAndDatumPrimjene(currency, LocalDate.now().toString());
        if (exchangeRateResponse.isPresent()) {
            return Double.parseDouble(exchangeRateResponse.get().getSrednjiTecaj().replace(",", "."));
        } else {
            return getNewExchangeRateIfNoneExists(currency);
        }


    }
}