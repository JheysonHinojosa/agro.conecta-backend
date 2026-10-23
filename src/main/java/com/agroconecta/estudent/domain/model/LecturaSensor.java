package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LecturaSensor extends BaseModel {

    private long idLectura;
    private long idSensor;
    private LocalDateTime fechaLectura;
    private BigDecimal temperaturaC;
    private BigDecimal humedadPct;
}
