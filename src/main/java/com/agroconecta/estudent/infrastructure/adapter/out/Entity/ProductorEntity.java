package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "productor")
@Getter
@Setter
@NoArgsConstructor
public class ProductorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_productor")
    private Long idProductor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_acopio")
    private CentroAcopioEntity centroAcopio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria_principal")
    private ParCategoriaProductoEntity categoriaPrincipal;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "activo")
    private boolean activo;
}