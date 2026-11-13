package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.AlertaRequest;
import com.agroconecta.estudent.application.dto.response.AlertaResponse;

import java.util.List;

public interface AlertaService {

    AlertaResponse crear(AlertaRequest request);

    List<AlertaResponse> listar();

    AlertaResponse buscarPorId(Long id);

    AlertaResponse actualizar(Long id, AlertaRequest request);

    void eliminar(Long id);
}