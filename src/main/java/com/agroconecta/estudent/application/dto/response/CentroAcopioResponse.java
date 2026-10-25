package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record CentroAcopioResponse(

         long idCentroAcopio,
         String codigo,
         String nombre,
         Integer idPais,
         String ciudad,
         String direccion,
         BigDecimal latitud,
         BigDecimal longitud,
         BigDecimal capacidadKg,
         String responsable,
         String telefono,
         boolean activo
) {
}
