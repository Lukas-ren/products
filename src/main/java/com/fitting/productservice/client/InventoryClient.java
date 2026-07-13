package com.fitting.productservice.client;

import com.fitting.productservice.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/v1/inventory/product/{productId}/available")
    ApiResponse<Boolean> isProductAvailable(@PathVariable Long productId);

    @GetMapping("/api/v1/inventory/product/{productId}/stock")
    ApiResponse<Integer> getStock(@PathVariable Long productId);
}