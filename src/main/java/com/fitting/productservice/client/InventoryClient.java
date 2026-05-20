package com.fitting.productservice.client;

import com.fitting.productservice.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Reemplaza la URL por variable de entorno en producción
@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {

    @GetMapping("/api/v1/inventory/product/{productId}/available")
    ApiResponse<Boolean> isProductAvailable(@PathVariable Long productId);

    @GetMapping("/api/v1/inventory/product/{productId}/stock")
    ApiResponse<Integer> getStock(@PathVariable Long productId);
}