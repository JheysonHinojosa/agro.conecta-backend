package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PedidoOperacionDetalleRequest(

        @NotNull(message = "El producto es requerido")
        @Positive(message = "El producto debe ser válido")
        Long idProducto,

        @NotNull(message = "El lote es requerido")
        @Positive(message = "El lote debe ser válido")
        Long idLote,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser mayor a cero")
        @Digits(integer = 12, fraction = 2, message = "La cantidad admite máximo 12 enteros y 2 decimales")
        BigDecimal cantidad
) {}
