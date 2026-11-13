package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.SensorIOTRequest;
import com.agroconecta.estudent.application.dto.response.SensorIOTResponse;

import java.util.List;

public interface SensorIOTService {

    SensorIOTResponse crear(SensorIOTRequest request);

    List<SensorIOTResponse> listar();

    SensorIOTResponse buscarPorId(Long id);

    SensorIOTResponse actualizar(Long id, SensorIOTRequest request);

    void eliminar(Long id);
}