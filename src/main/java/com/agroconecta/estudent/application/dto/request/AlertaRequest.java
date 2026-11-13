package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AlertaRequest(

        @NotNull(message = "El tipo de alerta es requerido")
        Integer idTipoAlerta,

        @NotNull(message = "El centro de acopio es requerido")
        Long idCentroAcopio,

        @NotNull(message = "El producto es requerido")
        Long idProducto,

        @NotNull(message = "El sensor es requerido")
        Long idSensor,

        @NotNull(message = "La lectura es requerida")
        Long idLectura,

        @NotBlank(message = "El mensaje es requerido")
        @Size(max = 300, message = "El mensaje admite máximo 300 caracteres")
        String mensaje,

        @NotNull(message = "El valor medido es requerido")
        BigDecimal valorMedido,

        @NotNull(message = "El valor umbral es requerido")
        BigDecimal valorUmbral,

        OffsetDateTime fechaAlerta,

        Boolean atendida,
        OffsetDateTime fechaAtencion,
        @Size(max = 60, message = "El campo atendida por admite máximo 60 caracteres")
        String atendidaPor
) {}