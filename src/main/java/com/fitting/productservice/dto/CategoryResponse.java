package com.fitting.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de la categoría retornados por la API")
public class CategoryResponse {

    @Schema(description = "ID de la categoría", example = "1")
    private Long id;

    @Schema(description = "Nombre de la categoría", example = "Camisetas")
    private String name;

    @Schema(description = "Descripción de la categoría", example = "Camisetas y poleras de algodón")
    private String description;
}