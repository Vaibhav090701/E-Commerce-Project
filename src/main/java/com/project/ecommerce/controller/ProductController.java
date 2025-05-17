package com.project.ecommerce.controller;

import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name="Products",description = "Product Search and Details APIs")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    @Operation(summary = "List/Search products", description = "List all products or search by name/brand")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String search) {
        List<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productRepository.findByNameOrBrandContaining(search.trim());
        } else {
            products = productRepository.findAll();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product details", description = "Fetch product details by ID")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(product);
    }

    @GetMapping("/ping")
    @Operation(summary = "Dummy Api", description = "To hit the api to make sure server run continuously")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
