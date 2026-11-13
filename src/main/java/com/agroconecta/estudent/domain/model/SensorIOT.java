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

     Long idSensor;
     String codigoSensor;
     Long idCentroAcopio;
     String descripcion;
     BigDecimal tempMinC;
     BigDecimal tempMaxC;
     BigDecimal humedadMinPct;
     BigDecimal humedadMaxPct;
     LocalDate fechaInstalacion;

    @Builder.Default
     boolean activo = true;
}