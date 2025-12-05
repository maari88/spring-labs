package com.cosmocats.multiverse_market.controller;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // === 1. READ (Get All with Filtering & Pagination) ===
    // Реалізація пункту 2.2: Додано @RequestParam для фільтрації та пагінації
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String planetId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Виклик оновленого методу сервісу
        List<Product> products = productService.findProducts(planetId, minPrice, page, size);
        return ResponseEntity.ok(products);
    }

    // === 2. READ (Get One by ID) ===
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // === 3. CREATE (Post) ===
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(null);
        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // === 4. UPDATE (Put) ===
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        product.setId(id);
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // === 5. DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}