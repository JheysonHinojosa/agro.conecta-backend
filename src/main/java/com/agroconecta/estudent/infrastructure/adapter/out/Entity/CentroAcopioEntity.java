package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "centro_acopio")
@Getter
@Setter
@NoArgsConstructor
public class CentroAcopioEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_centro_acopio")
    private Long idCentroAcopio;

    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "nombre", length = 120)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais")
    private ParPaisEntity pais;

    @Column(name = "ciudad", length = 80)
    private String ciudad;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "latitud", precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 9, scale = 6)
    private BigDecimal longitud;

    @Column(name = "capacidad_kg", precision = 14, scale = 2)
    private BigDecimal capacidadKg;

    @Column(name = "responsable", length = 120)
    private String responsable;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "activo")
    private boolean activo;
}