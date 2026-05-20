package com.fitting.productservice.controller;

import com.fitting.productservice.dto.ProductRequest;
import com.fitting.productservice.dto.ProductResponse;
import com.fitting.productservice.service.ProductService;
import com.fitting.productservice.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request) {

        log.info("POST /api/v1/products - Crear producto");
        ProductResponse response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Producto creado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(@PathVariable Long id) {
        log.info("GET /api/v1/products/{}", id);
        ProductResponse response = productService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto encontrado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findAll() {
        log.info("GET /api/v1/products");
        List<ProductResponse> response = productService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("Lista de productos", response));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findByCategory(
            @PathVariable Long categoryId) {

        log.info("GET /api/v1/products/category/{}", categoryId);
        List<ProductResponse> response = productService.findByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.ok("Productos de la categoría", response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> searchByName(
            @RequestParam String name) {

        log.info("GET /api/v1/products/search?name={}", name);
        List<ProductResponse> response = productService.searchByName(name);
        return ResponseEntity.ok(ApiResponse.ok("Resultados de búsqueda", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        log.info("PUT /api/v1/products/{}", id);
        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Producto actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/products/{}", id);
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto eliminado exitosamente", null));
    }
}
