package com.example.hnbproductservice.service;


import com.example.hnbproductservice.exception.DuplicateProductCodeException;
import com.example.hnbproductservice.model.Product;
import com.example.hnbproductservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    public Product createProduct(Product product) {
        if (productRepository.findByCode(product.getCode()).isPresent()) {
            throw new DuplicateProductCodeException("Product code already exists: " + product.getCode());
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        product.setPriceUsd(product.getPriceEur().multiply(BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"))));
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        BigDecimal usdExchangeRate = BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"));
        for (Product product : products) {
            product.setPriceUsd(product.getPriceEur().multiply(usdExchangeRate).setScale(2, RoundingMode.HALF_UP));
            productRepository.save(product);
        }
        return products;
    }
}