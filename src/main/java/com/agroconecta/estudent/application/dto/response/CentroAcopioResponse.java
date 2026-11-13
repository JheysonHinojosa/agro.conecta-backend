package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record CentroAcopioResponse(
        Long idCentroAcopio,
        String codigo,
        String nombre,
        Integer idPais,
        String nombrePais,
        String ciudad,
        String direccion,
        BigDecimal latitud,
        BigDecimal longitud,
        BigDecimal capacidadKg,
        String responsable,
        String telefono,
        boolean activo
) {}