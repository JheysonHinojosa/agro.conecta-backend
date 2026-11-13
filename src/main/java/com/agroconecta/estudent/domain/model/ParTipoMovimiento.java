package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParTipoMovimiento extends BaseModel {

     Integer idTipoMovimiento;
     String codigo;
     String nombre;
     String signo;

    @Builder.Default
     boolean activo = true;
}
