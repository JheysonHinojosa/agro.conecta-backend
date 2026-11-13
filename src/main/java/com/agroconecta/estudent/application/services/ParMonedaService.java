package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParMonedaRequest;
import com.agroconecta.estudent.application.dto.response.ParMonedaResponse;

import java.util.List;

public interface ParMonedaService {

    ParMonedaResponse crear(ParMonedaRequest request);

    List<ParMonedaResponse> listar();

    ParMonedaResponse buscarPorId(Integer id);

    ParMonedaResponse actualizar(Integer id, ParMonedaRequest request);

    void eliminar(Integer id);
}
