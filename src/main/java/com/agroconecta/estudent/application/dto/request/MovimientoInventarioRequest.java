package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record MovimientoInventarioRequest(

        @NotNull(message = "El inventario es requerido")
        Long idInventario,

        @NotNull(message = "El tipo de movimiento es requerido")
        Integer idTipoMovimiento,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser mayor a cero")
        BigDecimal cantidad,

        Long idPedido,

        Long idLote,

        OffsetDateTime fechaMovimiento,

        @Size(max = 300, message = "La observación admite máximo 300 caracteres")
        String observacion
) {}