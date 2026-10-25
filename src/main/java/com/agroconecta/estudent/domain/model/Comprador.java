package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Comprador extends BaseModel {

    private long idComprador;
    private Integer idTipoComprador;
    private Integer idTipoDocumento;
    private String nroDocumento;
    private String nombreRazonSocial;
    private String email;
    private String telefono;
    private String direccion;
    private Integer idPais;
    private String ciudad;

    @Builder.Default
    private boolean activo = true;
}
