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
public class SensorIOT extends BaseModel {

    private Long idSensor;
    private String codigoSensor;
    private Long idCentroAcopio;
    private String descripcion;
    private BigDecimal tempMinC;
    private BigDecimal tempMaxC;
    private BigDecimal humedadMinPct;
    private BigDecimal humedadMaxPct;
    private LocalDate fechaInstalacion;

    @Builder.Default
    private boolean activo = true;
}