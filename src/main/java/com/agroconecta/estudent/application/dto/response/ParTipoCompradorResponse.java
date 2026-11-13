package com.agroconecta.estudent.application.dto.response;

public record ParTipoCompradorResponse(

        Integer idTipoComprador,
        String codigo,
        String nombre,
        boolean activo
) {
}
