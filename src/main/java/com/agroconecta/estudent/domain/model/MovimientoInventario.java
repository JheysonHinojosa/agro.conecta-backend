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

     Long idMovimiento;
     Long idInventario;
     Integer idTipoMovimiento;
     BigDecimal cantidad;
     BigDecimal stockAnterior;
     BigDecimal stockPosterior;
     Long idPedido;
     Long idLote;
     LocalDateTime fechaMovimiento;
     String observacion;
}
