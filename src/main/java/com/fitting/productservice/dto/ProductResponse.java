package com.fitting.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos del producto retornados por la API")
public class ProductResponse {

    @Schema(description = "ID del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Camiseta Básica Blanca")
    private String name;

    @Schema(description = "Descripción del producto", example = "Camiseta de algodón 100%")
    private String description;

    @Schema(description = "Precio del producto", example = "19.99")
    private BigDecimal price;

    @Schema(description = "Stock disponible", example = "100")
    private Integer stock;

    @Schema(description = "Talla", example = "M")
    private String size;

    @Schema(description = "Color", example = "Blanco")
    private String color;

    @Schema(description = "Categoría del producto")
    private CategoryResponse category;
}