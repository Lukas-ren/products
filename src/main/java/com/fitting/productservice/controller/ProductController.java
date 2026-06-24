package com.fitting.productservice.controller;

import com.fitting.productservice.dto.ProductRequest;
import com.fitting.productservice.dto.ProductResponse;
import com.fitting.productservice.service.ProductService;
import com.fitting.productservice.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Productos", description = "Gestión del catálogo de productos")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Crear producto")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Producto creado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request) {
        log.info("POST /api/v1/products - Crear producto");
        ProductResponse response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Producto creado exitosamente", response));
    }

    @Operation(summary = "Listar productos")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findAll() {
        log.info("GET /api/v1/products");
        return ResponseEntity.ok(ApiResponse.ok("Lista de productos",
                productService.findAll()));
    }

    @Operation(summary = "Buscar producto por ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(@PathVariable Long id) {
        log.info("GET /api/v1/products/{}", id);
        return ResponseEntity.ok(ApiResponse.ok("Producto encontrado",
                productService.findById(id)));
    }

    @Operation(summary = "Buscar productos por categoría")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findByCategory(
            @PathVariable Long categoryId) {
        log.info("GET /api/v1/products/category/{}", categoryId);
        return ResponseEntity.ok(ApiResponse.ok("Productos de la categoría",
                productService.findByCategory(categoryId)));
    }

    @Operation(summary = "Buscar productos por nombre", description = "Búsqueda parcial por nombre")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Resultados obtenidos")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> searchByName(
            @RequestParam String name) {
        log.info("GET /api/v1/products/search?name={}", name);
        return ResponseEntity.ok(ApiResponse.ok("Resultados de búsqueda",
                productService.searchByName(name)));
    }

    @Operation(summary = "Actualizar producto")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        log.info("PUT /api/v1/products/{}", id);
        return ResponseEntity.ok(ApiResponse.ok("Producto actualizado exitosamente",
                productService.update(id, request)));
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/products/{}", id);
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Producto eliminado exitosamente", null));
    }
}