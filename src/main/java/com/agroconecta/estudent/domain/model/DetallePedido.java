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

     long idDetallePedido;
     long idPedido;
     long idProducto;
     long idLote;
     int cantidad;
     BigDecimal precioUnitario;
     BigDecimal subtotal;
}
