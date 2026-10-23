package com.agroconecta.estudent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido extends BaseModel{

    private long idDetallePedido;
    private long idPedido;
    private long idProducto;
    private long idLote;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
