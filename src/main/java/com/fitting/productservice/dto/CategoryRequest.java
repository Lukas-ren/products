package com.fitting.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos para crear o actualizar una categoría")
public class CategoryRequest {

    @Schema(description = "Nombre de la categoría", example = "Camisetas")
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100)
    private String name;

    @Schema(description = "Descripción de la categoría", example = "Camisetas y poleras de algodón")
    @Size(max = 255)
    private String description;
}