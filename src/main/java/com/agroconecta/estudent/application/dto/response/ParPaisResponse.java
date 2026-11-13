package com.agroconecta.estudent.application.dto.response;

public record ParPaisResponse(
        Integer idPais,
        String codigoIso,
        String nombre,
        String codigoTelefono,
        Integer idMoneda,
        String nombreMoneda,
        boolean activo
) {}