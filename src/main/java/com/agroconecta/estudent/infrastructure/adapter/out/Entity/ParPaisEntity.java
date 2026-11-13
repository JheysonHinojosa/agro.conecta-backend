package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_pais")
@Getter
@Setter
@NoArgsConstructor
public class ParPaisEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Integer idPais;

    @Column(name = "codigo_iso", length = 2)
    private String codigoIso;

    @Column(name = "nombre", length = 60)
    private String nombre;

    @Column(name = "codigo_telefono", length = 5)
    private String codigoTelefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moneda")
    private ParMonedaEntity moneda;

    @Column(name = "activo")
    private boolean activo;
}