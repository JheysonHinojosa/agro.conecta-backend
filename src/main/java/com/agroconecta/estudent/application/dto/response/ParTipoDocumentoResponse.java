package com.agroconecta.estudent.application.dto.response;

public record ParTipoDocumentoResponse(
        Integer idTipoDocumento,
        String codigo,
        String nombre,
        boolean activo
) {
}
