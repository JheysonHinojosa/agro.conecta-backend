package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompradorRequest(

        @NotNull(message = "El tipo de comprador es requerido")
        Integer idTipoComprador,

        @NotNull(message = "El tipo de documento es requerido")
        Integer idTipoDocumento,

        @NotBlank(message = "El número de documento es requerido")
        @Size(max = 20, message = "El número de documento admite máximo 20 caracteres")
        String nroDocumento,

        @NotBlank(message = "El nombre o razón social es requerido")
        @Size(max = 150, message = "El nombre o razón social admite máximo 150 caracteres")
        String nombreRazonSocial,

        @Email(message = "El email no es válido")
        @Size(max = 120, message = "El email admite máximo 120 caracteres")
        String email,

        @Size(max = 20, message = "El teléfono admite máximo 20 caracteres")
        String telefono,

        @Size(max = 200, message = "La dirección admite máximo 200 caracteres")
        String direccion,

        @NotNull(message = "El país es requerido")
        Integer idPais,

        @Size(max = 80, message = "La ciudad admite máximo 80 caracteres")
        String ciudad,

        Boolean activo
) {}