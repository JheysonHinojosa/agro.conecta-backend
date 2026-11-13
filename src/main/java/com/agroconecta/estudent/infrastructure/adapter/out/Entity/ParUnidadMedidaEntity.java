package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_unidad_medida")
@Getter
@Setter
@NoArgsConstructor
public class ParUnidadMedidaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad_medida")
    private Integer idUnidadMedida;

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombre", length = 40)
    private String nombre;

    @Column(name = "activo")
    private boolean activo;
}