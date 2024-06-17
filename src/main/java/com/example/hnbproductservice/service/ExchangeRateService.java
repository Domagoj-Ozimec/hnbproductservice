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

    /**
     * Retrieves the latest exchange rate from the HNB API for the specified currency.
     * If successful, saves the exchange rate to the database.
     * @param currency The currency code (e.g., USD, EUR) for which to fetch the exchange rate.
     * @return The fetched exchange rate as a double.
     * @throws RuntimeException if unable to fetch the exchange rate from the HNB API.
     */
    public double getNewExchangeRateIfNoneExists(String currency) {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateResponse[] response = restTemplate.getForObject(HNB_API_URL + currency, ExchangeRateResponse[].class);

        if (response != null && response.length > 0) {
            exchangeRateRepository.save(response[0]); // Save the fetched exchange rate data to the database
            // Parse and return the exchange rate as a double (replace comma with dot for correct parsing)
            return Double.parseDouble(response[0].getSrednjiTecaj().replace(",", "."));
        } else {
            throw new RuntimeException("Unable to fetch exchange rate from HNB API for currency: " + currency);
        }
    }

    /**
     * Retrieves the exchange rate for the specified currency from the database.
     * If the exchange rate for today's date does not exist in the database,
     * fetches the latest exchange rate from the HNB API and saves it to the database.
     * @param currency The currency code (e.g., USD, EUR) for which to retrieve the exchange rate.
     * @return The retrieved exchange rate as a double.
     * @throws RuntimeException if unable to fetch the exchange rate from the HNB API.
     */
    public double getExchangeRate(String currency) {
        Optional<ExchangeRateResponse> exchangeRateResponse = exchangeRateRepository.findByValutaAndDatumPrimjene(currency, LocalDate.now().toString());
        if (exchangeRateResponse.isPresent()) {
            // Parse and return the exchange rate from the database as a double (replace comma with dot for correct parsing)
            return Double.parseDouble(exchangeRateResponse.get().getSrednjiTecaj().replace(",", "."));
        } else {
            // If today's exchange rate doesn't exist in the database, fetch it from the HNB API
            return getNewExchangeRateIfNoneExists(currency);
        }
    }
}