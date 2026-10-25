package com.agroconecta.estudent.application.dto.response;


public record ProductorResponse(

        long idCentroAcopio,
        Integer idTipoDocumento,
        Integer idPais,
        Integer idCategoriaPrincipal,
        String nroDocumento,
        String nombreRazonSocial,
        String email,
        String telefono,
        String direccion
) {}
