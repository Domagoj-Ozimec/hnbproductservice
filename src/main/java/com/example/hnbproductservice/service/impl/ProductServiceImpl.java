package com.example.hnbproductservice.service.impl;


import com.example.hnbproductservice.exception.DuplicateProductCodeException;
import com.example.hnbproductservice.model.Product;
import com.example.hnbproductservice.repository.ProductRepository;
import com.example.hnbproductservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExchangeRateServiceImpl exchangeRateService;

    /**
     * Creates a new product in the database.
     * Throws DuplicateProductCodeException if the product code already exists.
     * @param product The product object to be created.
     * @return The created product object.
     * @throws DuplicateProductCodeException if the product code already exists in the database.
     */
    public Product createProduct(Product product) {
        if (productRepository.findByCode(product.getCode()).isPresent()) {
            throw new DuplicateProductCodeException("Product code already exists: " + product.getCode());
        }
        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID from the database.
     * Calculates and sets the price in USD based on the current exchange rate.
     * @param id The ID of the product to retrieve.
     * @return The product object with updated USD price.
     * @throws RuntimeException if no product with the specified ID is found.
     */
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        // Calculate price in USD based on EUR price and exchange rate, then update and save the product
        product.setPriceUsd(product.getPriceEur().multiply(BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"))).setScale(2, RoundingMode.HALF_UP));
        productRepository.save(product);
        return product;
    }

    /**
     * Retrieves all products from the database.
     * Calculates and sets the price in USD for each product based on the current exchange rate.
     * @return List of all products with updated USD prices.
     */
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        BigDecimal usdExchangeRate = BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"));

        // Iterate through each product, calculate USD price, update and save the product
        for (Product product : products) {
            product.setPriceUsd(product.getPriceEur().multiply(usdExchangeRate).setScale(2, RoundingMode.HALF_UP));
            productRepository.save(product);
        }
        return products; // Return the list of updated product objects
    }
}