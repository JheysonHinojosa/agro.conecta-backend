package com.agroconecta.estudent.application.dto;


public record RegistrarProductorCommand(

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
