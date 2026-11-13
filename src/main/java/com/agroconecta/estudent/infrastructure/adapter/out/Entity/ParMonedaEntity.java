package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_moneda")
@Getter
@Setter
@NoArgsConstructor
public class ParMonedaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moneda")
    private Integer idMoneda;

    @Column(name = "codigo", length = 3)
    private String codigo;

    @Column(name = "nombre", length = 60)
    private String nombre;

    @Column(name = "simbolo", length = 5)
    private String simbolo;

    @Column(name = "activo")
    private boolean activo;
}