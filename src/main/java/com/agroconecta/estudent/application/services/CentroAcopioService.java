package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.CentroAcopioRequest;
import com.agroconecta.estudent.application.dto.response.CentroAcopioResponse;

import java.util.List;

public interface CentroAcopioService {

    CentroAcopioResponse crear(CentroAcopioRequest request);

    List<CentroAcopioResponse> listar();

    CentroAcopioResponse buscarPorId(Long id);

    CentroAcopioResponse actualizar(Long id, CentroAcopioRequest request);

    void eliminar(Long id);
}