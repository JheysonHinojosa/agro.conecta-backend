package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.MovimientoInventarioRequest;
import com.agroconecta.estudent.application.dto.response.MovimientoInventarioResponse;

import java.util.List;

public interface MovimientoInventarioService {

    MovimientoInventarioResponse crear(MovimientoInventarioRequest request);

    List<MovimientoInventarioResponse> listar();

    MovimientoInventarioResponse buscarPorId(Long id);
}