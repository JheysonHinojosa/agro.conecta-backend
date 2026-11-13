package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParTipoAlertaRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoAlertaResponse;

import java.util.List;

public interface ParTipoAlertaService {

    ParTipoAlertaResponse crear(ParTipoAlertaRequest request);

    List<ParTipoAlertaResponse> listar();

    ParTipoAlertaResponse buscarPorId(Integer id);

    ParTipoAlertaResponse actualizar(Integer id, ParTipoAlertaRequest request);

    void eliminar(Integer id);
}
