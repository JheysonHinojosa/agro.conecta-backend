package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_tipo_alerta")
@Getter
@Setter
@NoArgsConstructor
public class ParTipoAlertaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_alerta")
    private Integer idTipoAlerta;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", length = 80)
    private String nombre;

    @Column(name = "activo")
    private boolean activo;
}