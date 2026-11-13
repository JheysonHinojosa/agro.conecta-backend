package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParRolRequest;
import com.agroconecta.estudent.application.dto.response.ParRolResponse;

import java.util.List;

public interface ParRolService {

    ParRolResponse crear(ParRolRequest request);

    List<ParRolResponse> listar();

    ParRolResponse buscarPorId(Integer id);

    ParRolResponse actualizar(Integer id, ParRolRequest request);

    void eliminar(Integer id);
}
