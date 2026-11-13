package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record DetallePedidoResponse(
        Long idDetallePedido,
        Long idPedido,
        Long idProducto,
        String nombreProducto,
        Long idLote,
        String codigoLote,
        BigDecimal cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {}