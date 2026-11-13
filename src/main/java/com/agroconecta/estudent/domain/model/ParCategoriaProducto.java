package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParCategoriaProducto extends BaseModel{

     Integer idCategoria;
     String codigo;
     String nombre;
     String descripcion;

    @Builder.Default
     boolean activo = true;
}
