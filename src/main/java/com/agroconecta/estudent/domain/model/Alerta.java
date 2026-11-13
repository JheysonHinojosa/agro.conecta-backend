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

     long idAlerta;
     Integer idTipoAlerta;
     long idCentroAcopio;
     long idProductor;
     long idSensor;
     long idLector;
     String mensaje;
     BigDecimal valorMedido;
     BigDecimal valorUmbral;
     LocalDateTime fechaAlerta;
     LocalDateTime fechaAtencion;
     String AtendidaPor;

    @Builder.Default
    private boolean atendida = true;
}
