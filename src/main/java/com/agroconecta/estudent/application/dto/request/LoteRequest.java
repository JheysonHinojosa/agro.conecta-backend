package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoteRequest(

        @NotBlank(message = "El código del lote es requerido")
        @Size(max = 30, message = "El código del lote admite máximo 30 caracteres")
        String codigoLote,

        @NotNull(message = "El producto es requerido")
        Long idProducto,

        @NotNull(message = "El centro de acopio es requerido")
        Long idCentroAcopio,

        @NotNull(message = "La fecha de cosecha es requerida")
        LocalDate fechaCosecha,

        @NotNull(message = "La fecha de ingreso es requerida")
        LocalDate fechaIngreso,

        @NotNull(message = "La fecha de vencimiento es requerida")
        LocalDate fechaVencimiento,

        @NotNull(message = "La cantidad ingresada es requerida")
        @Positive(message = "La cantidad ingresada debe ser mayor a cero")
        BigDecimal cantidadIngresada,

        @Size(max = 300, message = "La observación admite máximo 300 caracteres")
        String observacion,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}