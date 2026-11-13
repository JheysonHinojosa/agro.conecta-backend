package com.agroconecta.estudent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends BaseModel {

     Long idPedido;
     String codigoPedido;
     Long idComprador;
     Long idCentroAcopio;
     Integer idEstadoPedido;
     Integer idMoneda;
     LocalDate fechaPedido;
     LocalDate fechaEntregaEstimada;
     BigDecimal montoTotal;
     String observacion;
}