package com.example.hnbproductservice.service;


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
        product.setPriceUsd(product.getPriceEur().multiply(BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"))));
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        product.setPriceUsd(product.getPriceEur().multiply(BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"))));
        productRepository.updateById(product.getId());
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        BigDecimal usdExchangeRate = BigDecimal.valueOf(exchangeRateService.getExchangeRate("USD"));
        for (Product product : products) {
            product.setPriceUsd(product.getPriceEur().multiply(usdExchangeRate).setScale(2, RoundingMode.HALF_UP));
            productRepository.updateById(product.getId());
        }
        return products;
    }
}