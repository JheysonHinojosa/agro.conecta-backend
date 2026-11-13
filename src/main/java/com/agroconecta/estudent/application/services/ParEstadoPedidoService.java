package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.ParEstadoPedidoResponse;

import java.util.List;

public interface ParEstadoPedidoService {

    ParEstadoPedidoResponse crear(ParEstadoPedidoRequest request);

    List<ParEstadoPedidoResponse> listar();

    ParEstadoPedidoResponse buscarPorId(Integer id);

    ParEstadoPedidoResponse actualizar(Integer id, ParEstadoPedidoRequest request);

    void eliminar(Integer id);
}
