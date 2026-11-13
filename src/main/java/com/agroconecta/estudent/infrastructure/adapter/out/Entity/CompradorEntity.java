package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comprador")
@Getter
@Setter
@NoArgsConstructor
public class CompradorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprador")
    private Long idComprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_comprador")
    private ParTipoCompradorEntity tipoComprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_documento")
    private ParTipoDocumentoEntity tipoDocumento;

    @Column(name = "nro_documento", length = 20)
    private String nroDocumento;

    @Column(name = "nombre_razon_social", length = 150)
    private String nombreRazonSocial;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais")
    private ParPaisEntity pais;

    @Column(name = "ciudad", length = 80)
    private String ciudad;

    @Column(name = "activo")
    private boolean activo;
}