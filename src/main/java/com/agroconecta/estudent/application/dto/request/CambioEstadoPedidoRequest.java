package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CambioEstadoPedidoRequest(

        @NotNull(message = "El estado nuevo es requerido")
        Integer idEstadoNuevo,

        @Size(max = 300, message = "El comentario admite máximo 300 caracteres")
        String comentario
) {}