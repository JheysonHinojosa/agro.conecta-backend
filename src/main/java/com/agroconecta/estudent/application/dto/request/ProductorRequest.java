package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductorRequest(

        Long idCentroAcopio,

        @NotNull(message = "El tipo de documento es requerido")
        Integer idTipoDocumento,

        @NotNull(message = "El país es requerido")
        Integer idPais,

        @NotNull(message = "La categoría principal es requerida")
        Integer idCategoriaPrincipal,

        @NotBlank(message = "El número de documento es requerido")
        @Size(max = 20, message = "El número de documento admite máximo 20 caracteres")
        String nroDocumento,

        @NotBlank(message = "El nombre o razón social es requerido")
        @Size(max = 150, message = "El nombre o razón social admite máximo 150 caracteres")
        String nombreRazonSocial,

        @NotBlank(message = "El email es requerido")
        @Email(message = "El email no es válido")
        @Size(max = 120, message = "El email admite máximo 120 caracteres")
        String email,

        @NotBlank(message = "El teléfono es requerido")
        @Size(max = 20, message = "El teléfono admite máximo 20 caracteres")
        String telefono,

        @NotBlank(message = "La dirección es requerida")
        @Size(max = 200, message = "La dirección admite máximo 200 caracteres")
        String direccion,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}