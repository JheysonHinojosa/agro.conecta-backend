package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SensorIOTRequest(

        @NotBlank(message = "El código del sensor es requerido")
        @Size(max = 30, message = "El código del sensor admite máximo 30 caracteres")
        String codigoSensor,

        @NotNull(message = "El centro de acopio es requerido")
        Long idCentroAcopio,

        @NotBlank(message = "La descripción es requerida")
        @Size(max = 150, message = "La descripción admite máximo 150 caracteres")
        String descripcion,

        @NotNull(message = "La temperatura mínima es requerida")
        @DecimalMin(value = "-100.0", message = "La temperatura mínima permitida es -100 °C")
        @DecimalMax(value = "100.0", message = "La temperatura máxima permitida es 100 °C")
        @Digits(integer = 3, fraction = 2, message = "La temperatura admite máximo 2 decimales")
        BigDecimal tempMinC,

        @NotNull(message = "La temperatura máxima es requerida")
        @DecimalMin(value = "-100.0", message = "La temperatura mínima permitida es -100 °C")
        @DecimalMax(value = "100.0", message = "La temperatura máxima permitida es 100 °C")
        @Digits(integer = 3, fraction = 2, message = "La temperatura admite máximo 2 decimales")
        BigDecimal tempMaxC,

        @NotNull(message = "La humedad mínima es requerida")
        @DecimalMin(value = "0.0", message = "La humedad mínima es 0%")
        @DecimalMax(value = "100.0", message = "La humedad máxima es 100%")
        @Digits(integer = 3, fraction = 2, message = "La humedad admite máximo 2 decimales")
        BigDecimal humedadMinPct,

        @NotNull(message = "La humedad máxima es requerida")
        @DecimalMin(value = "0.0", message = "La humedad mínima es 0%")
        @DecimalMax(value = "100.0", message = "La humedad máxima es 100%")
        @Digits(integer = 3, fraction = 2, message = "La humedad admite máximo 2 decimales")
        BigDecimal humedadMaxPct,

        @NotNull(message = "La fecha de instalación es requerida")
        @PastOrPresent(message = "La fecha de instalación no puede ser futura")
        LocalDate fechaInstalacion,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {

        @AssertTrue(message = "La temperatura mínima debe ser menor que la máxima")
        public boolean isRangoTemperaturaValido() {
                return tempMinC == null || tempMaxC == null || tempMinC.compareTo(tempMaxC) < 0;
        }

        @AssertTrue(message = "La humedad mínima debe ser menor que la máxima")
        public boolean isRangoHumedadValido() {
                return humedadMinPct == null || humedadMaxPct == null || humedadMinPct.compareTo(humedadMaxPct) < 0;
        }
}