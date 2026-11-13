package com.agroconecta.estudent.application.dto.response;

import java.time.OffsetDateTime;

public record HistorialEstadoPedidoResponse(
        Long idHistorial,
        Long idPedido,
        Integer idEstadoAnterior,
        String nombreEstadoAnterior,
        Integer idEstadoNuevo,
        String nombreEstadoNuevo,
        OffsetDateTime fechaCambio,
        String comentario
) {}