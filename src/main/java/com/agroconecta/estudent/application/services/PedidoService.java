package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.CambioEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoRequest;
import com.agroconecta.estudent.application.dto.response.PedidoResponse;

import java.util.List;

public interface PedidoService {

    PedidoResponse crear(PedidoRequest request);

    List<PedidoResponse> listar();

    PedidoResponse buscarPorId(Long id);

    PedidoResponse actualizar(Long id, PedidoRequest request);

    PedidoResponse cambiarEstado(Long id, CambioEstadoPedidoRequest request);

    /** Recalcula monto_total como suma de subtotales. Lo debe llamar DetallePedidoService. */
    void recalcularMonto(Long idPedido);
}