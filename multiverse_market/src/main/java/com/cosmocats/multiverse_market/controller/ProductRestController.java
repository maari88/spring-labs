package com.cosmocats.multiverse_market.controller;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Товари", description = "Управління товарами через JDBC")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Отримати всі товари", description = "Повертає повний список товарів з бази даних.")
    @GetMapping
    public List<Product> getAll(@RequestParam(required = false) Long planetId) {
        if (planetId != null) {
            return productService.findByPlanet(planetId);
        }
        return productService.findAll();
    }

    @Operation(summary = "Знайти товар за ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Товар знайдено"),
            @ApiResponse(responseCode = "404", description = "Товар відсутній")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Створити новий товар", description = "ID генерується базою даних автоматично.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    @Operation(summary = "Оновити товар")
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.save(product);
    }

    @Operation(summary = "Видалити товар")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @Operation(summary = "Тест транзакції. Інфляція",
            description = "Змінює ціни всіх товарів на планеті. Якщо simulateError=true, зміни не мають зберегтися.")
    @PostMapping("/transaction/inflation")
    public ResponseEntity<String> applyInflation(
            @RequestParam Long planetId,
            @RequestParam double percentage,
            @RequestParam(defaultValue = "false") boolean simulateError) {
        try {
            productService.applyInflationToPlanet(planetId, percentage, simulateError);
            return ResponseEntity.ok("Інфляція застосована успішно!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Транзакція відкочена: " + e.getMessage());
        }
    }
}