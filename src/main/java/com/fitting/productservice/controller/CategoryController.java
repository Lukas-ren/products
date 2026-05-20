package com.fitting.productservice.controller;

import com.fitting.productservice.dto.CategoryRequest;
import com.fitting.productservice.dto.CategoryResponse;
import com.fitting.productservice.service.CategoryService;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryRequest request) {

        log.info("POST /api/v1/categories - Crear categoría");
        CategoryResponse response = categoryService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Categoría creada exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> findById(@PathVariable Long id) {
        log.info("GET /api/v1/categories/{}", id);
        CategoryResponse response = categoryService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok("Categoría encontrada", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findAll() {
        log.info("GET /api/v1/categories");
        List<CategoryResponse> response = categoryService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("Lista de categorías", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        log.info("PUT /api/v1/categories/{}", id);
        CategoryResponse response = categoryService.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Categoría actualizada exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/categories/{}", id);
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Categoría eliminada exitosamente", null));
    }
}