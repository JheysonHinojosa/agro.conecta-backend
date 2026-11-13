package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SensorIOTResponse(
        Long idSensor,
        String codigoSensor,
        Long idCentroAcopio,
        String nombreCentroAcopio,
        String descripcion,
        BigDecimal tempMinC,
        BigDecimal tempMaxC,
        BigDecimal humedadMinPct,
        BigDecimal humedadMaxPct,
        LocalDate fechaInstalacion,
        boolean activo
) {}