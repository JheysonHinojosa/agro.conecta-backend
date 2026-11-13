package com.agroconecta.estudent.application.dto.response;

public record ParMonedaResponse(

        Integer idMoneda,
        String codigo,
        String nombre,
        String simbolo,
        boolean activo
) {
}
