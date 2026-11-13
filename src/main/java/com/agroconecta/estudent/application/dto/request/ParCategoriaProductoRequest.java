package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParCategoriaProductoRequest(

        @NotBlank(message = "El código es requerido")
        @Size(max = 30, message = "El código admite máximo 30 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 60, message = "El nombre admite máximo 60 caracteres")
        String nombre,

        @Size(max = 200, message = "La descripción admite máximo 200 caracteres")
        String descripcion,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}