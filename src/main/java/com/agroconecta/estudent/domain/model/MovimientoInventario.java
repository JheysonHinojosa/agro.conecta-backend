package com.agroconecta.estudent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario extends BaseModel {

    private Long idMovimiento;
    private Long idInventario;
    private Integer idTipoMovimiento;
    private BigDecimal cantidad;
    private BigDecimal stockAnterior;
    private BigDecimal stockPosterior;
    private Long idPedido;
    private Long idLote;
    private LocalDateTime fechaMovimiento;
    private String observacion;
}
