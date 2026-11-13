package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ParEstadoPedidoRequest(

        @NotBlank(message = "El código es requerido")
        @Size(max = 20, message = "El código admite máximo 20 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 40, message = "El nombre admite máximo 40 caracteres")
        String nombre,

        @NotNull(message = "El orden es requerido")
        @PositiveOrZero(message = "El orden no puede ser negativo")
        Short orden,

        @NotNull(message = "El campo esFinal es requerido")
        Boolean esFinal,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}