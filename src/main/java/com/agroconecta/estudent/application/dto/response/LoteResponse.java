package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoteResponse(
        Long idLote,
        String codigoLote,
        Long idProducto,
        String nombreProducto,
        Long idCentroAcopio,
        String nombreCentroAcopio,
        LocalDate fechaCosecha,
        LocalDate fechaIngreso,
        LocalDate fechaVencimiento,
        BigDecimal cantidadIngresada,
        String observacion,
        boolean activo
) {}