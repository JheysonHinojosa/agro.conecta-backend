package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record DetallePedidoResponse(

         long idDetallePedido,
         long idPedido,
         long idProducto,
         long idLote,
         int cantidad,
         BigDecimal precioUnitario,
         BigDecimal subtotal
) {
}
