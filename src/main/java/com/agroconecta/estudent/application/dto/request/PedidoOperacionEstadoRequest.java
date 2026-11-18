package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record PedidoOperacionEstadoRequest(

        @NotNull(message = "El estado nuevo es requerido")
        @Positive(message = "El estado debe ser válido")
        Integer idEstadoNuevo,

        @Size(max = 300, message = "El comentario admite máximo 300 caracteres")
        String comentario
) {}
