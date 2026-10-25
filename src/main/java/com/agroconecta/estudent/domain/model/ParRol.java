package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParRol extends BaseModel {

    private Integer idRol;
    private String codigo;
    private String nombre;
    private String descripcion;

    @Builder.Default
    private boolean activo = true;
}
