package com.agroconecta.estudent.application.dto.response;

public record ParTipoAlertaResponse(

        Integer idTipoAlerta,
        String codigo,
        String nombre,
        boolean activo
) {
}
