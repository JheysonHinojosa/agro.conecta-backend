package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoRequest(

        @NotBlank(message = "El código es requerido")
        @Size(max = 30, message = "El código admite máximo 30 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 150, message = "El nombre admite máximo 150 caracteres")
        String nombre,

        @Size(max = 300, message = "La descripción admite máximo 300 caracteres")
        String descripcion,

        @NotNull(message = "La categoría es requerida")
        Integer idCategoria,

        @NotNull(message = "La unidad de medida es requerida")
        Integer idUnidadMedida,

        @NotNull(message = "El precio referencial es requerido")
        @PositiveOrZero(message = "El precio referencial no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El precio admite hasta 10 enteros y 2 decimales")
        BigDecimal precioReferencial,

        @NotNull(message = "La moneda es requerida")
        Integer idMoneda,

        @NotNull(message = "El productor es requerido")
        Long idProductor,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}