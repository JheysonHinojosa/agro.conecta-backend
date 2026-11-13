package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.HistorialEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.HistorialEstadoPedidoResponse;

import java.util.List;

public interface HistorialEstadoPedidoService {

    HistorialEstadoPedidoResponse crear(HistorialEstadoPedidoRequest request);

    List<HistorialEstadoPedidoResponse> listar();

    HistorialEstadoPedidoResponse buscarPorId(Long id);

    HistorialEstadoPedidoResponse actualizar(Long id, HistorialEstadoPedidoRequest request);

    void eliminar(Long id);
}