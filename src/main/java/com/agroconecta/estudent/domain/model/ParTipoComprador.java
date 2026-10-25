package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParTipoComprador extends BaseModel {

    private Integer idTipoComprador;
    private String codigo;
    private String nombre;

    @Builder.Default
    private boolean activo = true;
}
