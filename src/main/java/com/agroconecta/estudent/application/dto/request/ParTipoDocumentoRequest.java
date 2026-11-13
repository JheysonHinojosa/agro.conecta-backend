package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParTipoDocumentoRequest(

        @NotBlank(message = "El código es requerido")
        @Size(max = 10, message = "El código admite máximo 10 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 80, message = "El nombre admite máximo 80 caracteres")
        String nombre,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}