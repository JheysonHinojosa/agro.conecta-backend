package com.agroconecta.estudent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstadoPedido extends BaseModel {

    private long idHistorial;
    private long idPedido;
    private Integer idEstadoAnterior;
    private Integer idEstadoNuevo;
    private LocalDateTime fechaCambio;
    private String comentario;
}
