package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DetallePedidoRequest(

        @NotNull(message = "El pedido es requerido")
        Long idPedido,

        @NotNull(message = "El producto es requerido")
        Long idProducto,

        @NotNull(message = "El lote es requerido")
        Long idLote,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser mayor a cero")
        BigDecimal cantidad,

        @NotNull(message = "El precio unitario es requerido")
        @PositiveOrZero(message = "El precio unitario no puede ser negativo")
        BigDecimal precioUnitario
) {}