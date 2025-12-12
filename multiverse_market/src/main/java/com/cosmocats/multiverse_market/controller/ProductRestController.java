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

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management API", description = "Контролер для управління товарами міжгалактичного маркетплейсу")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // --- 1. GET ALL ---
    @Operation(
            summary = "Отримати всі товари",
            description = "Повертає повний список товарів. Можна фільтрувати за ID планети."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успішна операція",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)))
    })
    @GetMapping
    public List<Product> getAll(
            @Parameter(description = "ID планети для фільтрації (опціонально)")
            @RequestParam(required = false) Long planetId) {
        if (planetId != null) {
            return productService.findByPlanet(planetId);
        }
        return productService.findAll();
    }

    // --- 2. GET BY ID ---
    @Operation(summary = "Знайти товар за ID", description = "Повертає один товар за його унікальним ідентифікатором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар знайдено"),
            @ApiResponse(responseCode = "404", description = "Товар не знайдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(
            @Parameter(description = "Унікальний ID товару") @PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- 3. CREATE ---
    @Operation(summary = "Створити новий товар", description = "Створює новий запис про товар у базі даних.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Товар успішно створено"),
            @ApiResponse(responseCode = "400", description = "Некоректні дані")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    // --- 4. UPDATE ---
    @Operation(summary = "Оновити товар", description = "Оновлює існуючий товар за ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар оновлено"),
            @ApiResponse(responseCode = "404", description = "Товар не знайдено для оновлення")
    })
    @PutMapping("/{id}")
    public Product update(
            @Parameter(description = "ID товару, який оновлюється") @PathVariable Long id,
            @RequestBody Product product) {
        product.setId(id);
        return productService.save(product);
    }

    // --- 5. DELETE ---
    @Operation(summary = "Видалити товар", description = "Видаляє запис про товар з бази даних.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Товар видалено успішно"),
            @ApiResponse(responseCode = "500", description = "Помилка сервера (наприклад, порушення цілісності даних)")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "ID товару для видалення") @PathVariable Long id) {
        productService.deleteById(id);
    }

    // --- 6. TRANSACTION TEST ---
    @Operation(
            summary = "Тест транзакції (Інфляція)",
            description = "Змінює ціни всіх товарів на вказаній планеті на певний відсоток. " +
                    "Якщо параметр simulateError=true, виникає помилка і всі зміни відкочуються (ROLLBACK)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Інфляція застосована успішно (COMMIT)"),
            @ApiResponse(responseCode = "500", description = "Транзакцію відкочено через помилку (ROLLBACK)")
    })
    @PostMapping("/transaction/inflation")
    public ResponseEntity<String> applyInflation(
            @Parameter(description = "ID планети (1=Земля, 2=Марс)") @RequestParam Long planetId,
            @Parameter(description = "Відсоток інфляції (наприклад, 10.0)") @RequestParam double percentage,
            @Parameter(description = "Чи симулювати помилку для перевірки відкату?") @RequestParam(defaultValue = "false") boolean simulateError) {
        try {
            productService.applyInflationToPlanet(planetId, percentage, simulateError);
            return ResponseEntity.ok("Інфляція застосована успішно! Ціни оновлено.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Транзакція відкочена! Ціни залишились старими. Помилка: " + e.getMessage());
        }
    }
}