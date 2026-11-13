package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_tipo_comprador")
@Getter
@Setter
@NoArgsConstructor
public class ParTipoCompradorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_comprador")
    private Integer idTipoComprador;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", length = 60)
    private String nombre;

    @Column(name = "activo")
    private boolean activo;
}