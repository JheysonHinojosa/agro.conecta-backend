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

     long idResena;
     long idLote;
     long idComprador;
     long idPedido;
     String puntuacion;
     String comentario;
     LocalDate fechaResena;
}
