package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.response.SensorHistorialResponse;

import java.time.LocalDate;

public interface SensorHistorialService {

    SensorHistorialResponse consultar(Long idCentroAcopio, LocalDate desde, LocalDate hasta, int page, int pageSize);
}
