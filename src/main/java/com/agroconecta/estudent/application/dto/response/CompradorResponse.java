package com.agroconecta.estudent.application.dto.response;

public record CompradorResponse(
        Long idComprador,
        Integer idTipoComprador,
        String nombreTipoComprador,
        Integer idTipoDocumento,
        String nombreTipoDocumento,
        String nroDocumento,
        String nombreRazonSocial,
        String email,
        String telefono,
        String direccion,
        Integer idPais,
        String nombrePais,
        String ciudad,
        boolean activo
) {}