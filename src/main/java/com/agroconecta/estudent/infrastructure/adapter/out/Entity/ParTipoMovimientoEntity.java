package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_tipo_movimiento")
@Getter
@Setter
@NoArgsConstructor
public class ParTipoMovimientoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_movimiento")
    private Integer idTipoMovimiento;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", length = 60)
    private String nombre;

    @Column(name = "signo")
    private Short signo;

    @Column(name = "activo")
    private boolean activo;
}