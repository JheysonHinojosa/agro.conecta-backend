package com.agroconecta.estudent.application.dto.response;

public record CompradorResponse(

         long idComprador,
         Integer idTipoComprador,
         Integer idTipoDocumento,
         String nroDocumento,
         String nombreRazonSocial,
         String email,
         String telefono,
         String direccion,
         Integer idPais,
         String ciudad,
         boolean activo
) {
}
