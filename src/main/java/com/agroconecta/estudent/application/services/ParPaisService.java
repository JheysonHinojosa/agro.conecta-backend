package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParPaisRequest;
import com.agroconecta.estudent.application.dto.response.ParPaisResponse;

import java.util.List;

public interface ParPaisService {

    ParPaisResponse crear(ParPaisRequest request);

    List<ParPaisResponse> listar();

    ParPaisResponse buscarPorId(Integer id);

    ParPaisResponse actualizar(Integer id, ParPaisRequest request);

    void eliminar(Integer id);
}
