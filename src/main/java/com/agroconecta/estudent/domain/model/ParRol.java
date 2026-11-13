package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParRol extends BaseModel {

     Integer idRol;
     String codigo;
     String nombre;
     String descripcion;

    @Builder.Default
     boolean activo = true;
}
