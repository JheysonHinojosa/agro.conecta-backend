package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParTipoMovimientoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoMovimientoResponse;

import java.util.List;

public interface ParTipoMovimientoService {

    ParTipoMovimientoResponse crear(ParTipoMovimientoRequest request);

    List<ParTipoMovimientoResponse> listar();

    ParTipoMovimientoResponse buscarPorId(Integer id);

    ParTipoMovimientoResponse actualizar(Integer id, ParTipoMovimientoRequest request);

    void eliminar(Integer id);

}
