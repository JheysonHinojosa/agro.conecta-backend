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

    private Long idPedido;
    private String codigoPedido;
    private Long idComprador;
    private Long idCentroAcopio;
    private Integer idEstadoPedido;
    private Integer idMoneda;
    private LocalDate fechaPedido;
    private LocalDate fechaEntregaEstimada;
    private BigDecimal montoTotal;
    private String observacion;
}