package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParTipoDocumento extends BaseModel {

     Integer idTipoDocumento;
     String codigo;
     String nombre;

    @Builder.Default
     boolean activo = true;
}
