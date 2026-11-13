package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "alerta")
@Getter
@Setter
@NoArgsConstructor
public class AlertaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_alerta")
    private ParTipoAlertaEntity tipoAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_acopio")
    private CentroAcopioEntity centroAcopio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sensor")
    private SensorIotEntity sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lectura")
    private LecturaSensorEntity lectura;

    @Column(name = "mensaje", length = 300)
    private String mensaje;

    @Column(name = "valor_medido", precision = 14, scale = 2)
    private BigDecimal valorMedido;

    @Column(name = "valor_umbral", precision = 14, scale = 2)
    private BigDecimal valorUmbral;

    @Column(name = "fecha_alerta")
    private OffsetDateTime fechaAlerta;

    @Column(name = "atendida")
    private boolean atendida;

    @Column(name = "fecha_atencion")
    private OffsetDateTime fechaAtencion;

    @Column(name = "atendida_por", length = 60)
    private String atendidaPor;
}