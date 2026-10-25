package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParTipoDocumento extends BaseModel {

    private Integer idTipoDocumento;;
    private String codigo;
    private String nombre;

    @Builder.Default
    private boolean activo = true;
}
