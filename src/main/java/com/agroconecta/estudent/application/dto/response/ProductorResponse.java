package com.agroconecta.estudent.application.dto.response;

import java.time.LocalDate;

public record ProductorResponse(
        Long idProductor,
        Long idCentroAcopio,
        String nombreCentroAcopio,
        Integer idTipoDocumento,
        String nombreTipoDocumento,
        Integer idPais,
        String nombrePais,
        Integer idCategoriaPrincipal,
        String nombreCategoriaPrincipal,
        String nroDocumento,
        String nombreRazonSocial,
        String email,
        String telefono,
        String direccion,
        LocalDate fechaRegistro,
        boolean activo
) {}