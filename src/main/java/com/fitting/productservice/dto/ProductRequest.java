package com.fitting.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos para crear o actualizar un producto")
public class ProductRequest {

    @Schema(description = "Nombre del producto", example = "Camiseta Básica Blanca")
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 150)
    private String name;

    @Schema(description = "Descripción del producto", example = "Camiseta de algodón 100%")
    @Size(max = 500)
    private String description;

    @Schema(description = "Precio del producto", example = "19.99")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Schema(description = "Stock disponible", example = "100")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0)
    private Integer stock;

    @Schema(description = "Talla del producto", example = "M",
            allowableValues = {"XS","S","M","L","XL","XXL","U"})
    @Size(max = 50)
    private String size;

    @Schema(description = "Color del producto", example = "Blanco")
    @Size(max = 50)
    private String color;

    @Schema(description = "ID de la categoría", example = "1")
    @NotNull(message = "El ID de categoría es obligatorio")
    private Long categoryId;
}