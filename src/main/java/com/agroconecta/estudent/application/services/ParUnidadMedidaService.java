package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParUnidadMedidaRequest;
import com.agroconecta.estudent.application.dto.response.ParUnidadMedidaResponse;

import java.util.List;

public interface ParUnidadMedidaService {

    ParUnidadMedidaResponse crear(ParUnidadMedidaRequest request);

    List<ParUnidadMedidaResponse> listar();

    ParUnidadMedidaResponse buscarPorId(Integer id);

    ParUnidadMedidaResponse actualizar(Integer id, ParUnidadMedidaRequest request);

    void eliminar(Integer id);
}
