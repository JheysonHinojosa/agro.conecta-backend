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

    private Long idLote;
    private String codigoLote;
    private Long idProducto;
    private Long idCentroAcopio;
    private LocalDate fechaCosecha;
    private LocalDate fechaIngreso;
    private LocalDate fechaVencimiento;
    private BigDecimal cantidadIngresada;
    private String observacion;

    @Builder.Default
    private boolean activo = true;
}
