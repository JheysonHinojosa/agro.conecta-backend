package com.agroconecta.estudent.application.dto.response;

public record ParTipoMovimientoResponse(
        Integer idTipoMovimiento,
        String codigo,
        String nombre,
        Short signo,
        boolean activo
) {}