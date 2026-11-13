package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Lote extends BaseModel {

     Long idLote;
     String codigoLote;
     Long idProducto;
     Long idCentroAcopio;
     LocalDate fechaCosecha;
     LocalDate fechaIngreso;
     LocalDate fechaVencimiento;
     BigDecimal cantidadIngresada;
     String observacion;

    @Builder.Default
     boolean activo = true;
}
