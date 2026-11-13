package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record HistorialEstadoPedidoRequest(

        @NotNull(message = "El pedido es requerido")
        Long idPedido,

        Integer idEstadoAnterior,

        @NotNull(message = "El estado nuevo es requerido")
        Integer idEstadoNuevo,

        OffsetDateTime fechaCambio,

        @Size(max = 300, message = "El comentario admite máximo 300 caracteres")
        String comentario
) {}