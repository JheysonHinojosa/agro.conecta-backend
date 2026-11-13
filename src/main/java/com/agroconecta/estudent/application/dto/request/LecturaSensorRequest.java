package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record LecturaSensorRequest(

        @NotNull(message = "El sensor es requerido")
        Long idSensor,

        OffsetDateTime fechaLectura,

        @NotNull(message = "La temperatura es requerida")
        @DecimalMin(value = "-100.0", message = "La temperatura mínima es -100 °C")
        @DecimalMax(value = "100.0", message = "La temperatura máxima es 100 °C")
        @Digits(integer = 3, fraction = 2, message = "La temperatura admite máximo 2 decimales")
        BigDecimal temperaturaC,

        @NotNull(message = "La humedad es requerida")
        @DecimalMin(value = "0.0", message = "La humedad mínima es 0%")
        @DecimalMax(value = "100.0", message = "La humedad máxima es 100%")
        @Digits(integer = 3, fraction = 2, message = "La humedad admite máximo 2 decimales")
        BigDecimal humedadPct
) {}