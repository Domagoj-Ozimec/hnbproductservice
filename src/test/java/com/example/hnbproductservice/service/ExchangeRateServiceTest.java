package com.example.hnbproductservice.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import com.example.hnbproductservice.model.ExchangeRateResponse;
import com.example.hnbproductservice.repository.ExchangeRateRepository;
import com.example.hnbproductservice.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExchangeRate_FromRepository() {
        ExchangeRateResponse response = new ExchangeRateResponse();
        response.setSrednjiTecaj("7,53450");
        when(exchangeRateRepository.findByValutaAndDatumPrimjene(anyString(), anyString()))
                .thenReturn(Optional.of(response));

        double exchangeRate = exchangeRateService.getExchangeRate("USD");

        assertEquals(7.53450, exchangeRate);
        verify(exchangeRateRepository).findByValutaAndDatumPrimjene("USD", LocalDate.now().toString());
    }

}
