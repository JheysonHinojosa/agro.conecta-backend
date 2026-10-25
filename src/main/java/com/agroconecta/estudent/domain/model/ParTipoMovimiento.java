package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParTipoMovimiento extends BaseModel {

    private Integer idTipoMovimiento;
    private String codigo;
    private String nombre;
    private String signo;

    @Builder.Default
    private boolean activo = true;
}
