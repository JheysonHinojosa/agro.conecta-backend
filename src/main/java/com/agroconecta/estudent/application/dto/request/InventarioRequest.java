package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record InventarioRequest(

        @NotNull(message = "El centro de acopio es requerido")
        Long idCentroAcopio,

        @NotNull(message = "El producto es requerido")
        Long idProducto,

        @NotNull(message = "El stock actual es requerido")
        @PositiveOrZero(message = "El stock actual no puede ser negativo")
        BigDecimal stockActual,

        @NotNull(message = "El stock mínimo es requerido")
        @PositiveOrZero(message = "El stock mínimo no puede ser negativo")
        BigDecimal stockMinimo
) {}