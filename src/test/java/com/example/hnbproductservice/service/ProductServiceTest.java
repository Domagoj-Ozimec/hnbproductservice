package com.example.hnbproductservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.hnbproductservice.model.Product;
import com.example.hnbproductservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertEquals(product.getId(), createdProduct.getId());
        verify(productRepository).save(product);
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setPriceEur(BigDecimal.valueOf(100));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(exchangeRateService.getExchangeRate("USD")).thenReturn(1.2);

        Product fetchedProduct = productService.getProductById(1L);

        assertEquals(BigDecimal.valueOf(120.0), fetchedProduct.getPriceUsd());
        verify(productRepository).findById(1L);
        verify(productRepository).save(product);
    }

    @Test
    void testGetProductById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product not found with id 1", exception.getMessage());
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setPriceEur(BigDecimal.valueOf(100));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setPriceEur(BigDecimal.valueOf(200));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(exchangeRateService.getExchangeRate("USD")).thenReturn(1.2);

        List<Product> products = productService.getAllProducts();

        assertEquals(BigDecimal.valueOf(120.00).setScale(2, RoundingMode.HALF_UP), products.get(0).getPriceUsd());
        assertEquals(BigDecimal.valueOf(240.00).setScale(2, RoundingMode.HALF_UP), products.get(1).getPriceUsd());

        verify(productRepository).findAll();
        verify(productRepository).save(product1);
        verify(productRepository).save(product2);
    }
}
