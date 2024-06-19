package com.example.hnbproductservice.controller;

import com.example.hnbproductservice.exception.DuplicateProductCodeException;
import com.example.hnbproductservice.model.Product;
import com.example.hnbproductservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint to create a new product.
     * @param product The product object to be created (sent in the request body as JSON).
     * @return ResponseEntity containing the created product and HTTP status CREATED (201).
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve a product by its ID.
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the retrieved product and HTTP status OK (200), or NOT_FOUND (404) if product not found.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> product = Optional.ofNullable(productService.getProductById(id));
        return product.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to retrieve all products.
     * @return List of all products retrieved from ProductServiceImpl.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Endpoint to show a form for creating a new product.
     * @param model The model object to add attributes needed for rendering the view.
     * @return View name (product_form) to display the product creation form.
     */
    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new Product());
        }
        return "product_form";
    }

    /**
     * Endpoint to submit a form for creating a new product.
     * @param product The product object submitted from the form.
     * @param redirectAttributes Attributes to add flash attributes for redirecting with error messages or data.
     * @return Redirect to view all products if successful, or redirect back to the product creation form if a DuplicateProductCodeException occurs.
     */
    @PostMapping("/products")
    public String submitProductForm(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.createProduct(product);
            return "redirect:/api/products";
        } catch (DuplicateProductCodeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("product", product);
            return "redirect:/api/products/new";
        }
    }

    /**
     * Exception handler for handling DuplicateProductCodeException.
     * Redirects back to the product creation form with an error message.
     * @param ex The DuplicateProductCodeException that was thrown.
     * @param redirectAttributes Attributes to add flash attributes for redirecting with error messages.
     * @return Redirect back to the product creation form with an error message.
     */
    @ExceptionHandler(DuplicateProductCodeException.class)
    public String handleDuplicateProductCodeException(DuplicateProductCodeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/api/products/new";
    }

    /**
     * Endpoint to view all products.
     * @param model The model object to add attributes needed for rendering the view.
     * @return View name (products) to display all products.
     */
    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }
}