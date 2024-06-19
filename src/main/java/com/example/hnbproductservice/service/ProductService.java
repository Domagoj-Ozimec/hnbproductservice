package com.example.hnbproductservice.service;

import com.example.hnbproductservice.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();
}
