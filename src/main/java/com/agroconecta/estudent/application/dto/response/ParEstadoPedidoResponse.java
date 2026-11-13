package com.agroconecta.estudent.application.dto.response;

public record ParEstadoPedidoResponse(
        Integer idEstadoPedido,
        String codigo,
        String nombre,
        Short orden,
        boolean esFinal,
        boolean activo
) {}