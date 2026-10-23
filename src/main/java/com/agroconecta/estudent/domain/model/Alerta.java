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
public class Alerta extends BaseModel {

    private long idAlerta;
    private Integer idTipoAlerta;
    private long idCentroAcopio;
    private long idProductor;
    private long idSensor;
    private long idLector;
    private String mensaje;
    private BigDecimal valorMedido;
    private BigDecimal valorUmbral;
    private LocalDateTime fechaAlerta;
    private LocalDateTime fechaAtencion;
    private String AtendidaPor;

    @Builder.Default
    private boolean atendida = true;
}
