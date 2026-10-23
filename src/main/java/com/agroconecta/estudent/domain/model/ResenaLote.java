package com.agroconecta.estudent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResenaLote extends BaseModel{

    private long idResena;
    private long idLote;
    private long idComprador;
    private long idPedido;
    private String puntuacion;
    private String comentario;
    private LocalDate fechaResena;
}
