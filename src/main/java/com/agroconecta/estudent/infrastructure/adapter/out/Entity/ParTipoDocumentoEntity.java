package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_tipo_documento")
@Getter
@Setter
@NoArgsConstructor
public class ParTipoDocumentoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_documento")
    private Integer idTipoDocumento;

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombre", length = 80)
    private String nombre;

    @Column(name = "activo")
    private boolean activo;
}