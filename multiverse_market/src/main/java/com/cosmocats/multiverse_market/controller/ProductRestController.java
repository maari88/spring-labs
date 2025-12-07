package com.cosmocats.multiverse_market.controller;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Товари", description = "API для управління товарами (CRUD, пошук, patch)")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(summary = "Отримати список товарів", description = "Повертає список товарів з фільтрацією та пагінацією.")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @Parameter(description = "ID планети") @RequestParam(required = false) String planetId,
            @Parameter(description = "Мін. ціна") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Сторінка (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Розмір сторінки") @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.findProducts(planetId, minPrice, page, size));
    }


    @Operation(summary = "Отримати товар по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Знайдено"),
            @ApiResponse(responseCode = "404", description = "Не знайдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Створити товар")
    @ApiResponse(responseCode = "201", description = "Створено")
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }


    @Operation(summary = "Повне оновлення товару")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        if (productService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        product.setId(id);
        return ResponseEntity.ok(productService.saveProduct(product));
    }


    @Operation(summary = "Часткове оновлення (ціна, опис тощо)")
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        Product updated = productService.updateProductPartially(id, updates);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Видалити товар")
    @ApiResponse(responseCode = "204", description = "Видалено успішно")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (productService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}