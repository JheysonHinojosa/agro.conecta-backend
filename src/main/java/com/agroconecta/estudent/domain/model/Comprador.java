package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Comprador extends BaseModel {

     long idComprador;
     Integer idTipoComprador;
     Integer idTipoDocumento;
     String nroDocumento;
     String nombreRazonSocial;
     String email;
     String telefono;
     String direccion;
     Integer idPais;
     String ciudad;

    @Builder.Default
     boolean activo = true;
}
