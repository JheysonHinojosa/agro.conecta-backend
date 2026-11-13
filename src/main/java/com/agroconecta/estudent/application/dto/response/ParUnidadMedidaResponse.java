package com.agroconecta.estudent.application.dto.response;

public record ParUnidadMedidaResponse(
        Integer idUnidadMedida,
        String codigo,
        String nombre,
        boolean activo
) {}