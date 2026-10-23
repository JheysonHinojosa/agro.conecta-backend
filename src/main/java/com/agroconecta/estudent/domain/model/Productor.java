package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Productor extends BaseModel {

    private Long idProductor;
    private Long idCentroAcopio;
    private Integer idTipoDocumento;
    private Integer idPais;
    private Integer idCategoriaPrincipal;
    private String nroDocumento;
    private String nombreRazonSocial;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;

    @Builder.Default
    private boolean activo = true;
}