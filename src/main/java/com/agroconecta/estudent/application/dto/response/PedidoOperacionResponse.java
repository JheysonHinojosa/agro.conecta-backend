package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record PedidoOperacionResponse(
        Long idPedido,
        String codigoPedido,
        Long idComprador,
        String nombreComprador,
        Long idCentroAcopio,
        String nombreCentroAcopio,
        Integer idEstadoPedido,
        String nombreEstadoPedido,
        boolean estadoFinal,
        Integer idMoneda,
        String codigoMoneda,
        OffsetDateTime fechaPedido,
        LocalDate fechaEntregaEstimada,
        BigDecimal montoTotal,
        String observacion,
        List<DetallePedidoResponse> detalle,
        List<HistorialEstadoPedidoResponse> historial
) {

    public static PedidoOperacionResponse from(
            PedidoResponse pedido,
            List<DetallePedidoResponse> detalle,
            List<HistorialEstadoPedidoResponse> historial) {
        return new PedidoOperacionResponse(
                pedido.idPedido(),
                pedido.codigoPedido(),
                pedido.idComprador(),
                pedido.nombreComprador(),
                pedido.idCentroAcopio(),
                pedido.nombreCentroAcopio(),
                pedido.idEstadoPedido(),
                pedido.nombreEstadoPedido(),
                pedido.estadoFinal(),
                pedido.idMoneda(),
                pedido.codigoMoneda(),
                pedido.fechaPedido(),
                pedido.fechaEntregaEstimada(),
                pedido.montoTotal(),
                pedido.observacion(),
                detalle,
                historial
        );
    }
}
