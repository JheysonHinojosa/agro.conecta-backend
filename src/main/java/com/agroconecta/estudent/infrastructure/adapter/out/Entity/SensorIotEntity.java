package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sensor_iot")
@Getter
@Setter
@NoArgsConstructor
public class SensorIotEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long idSensor;

    @Column(name = "codigo_sensor", length = 30)
    private String codigoSensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_acopio")
    private CentroAcopioEntity centroAcopio;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "temp_min_c", precision = 5, scale = 2)
    private BigDecimal tempMinC;

    @Column(name = "temp_max_c", precision = 5, scale = 2)
    private BigDecimal tempMaxC;

    @Column(name = "humedad_min_pct", precision = 5, scale = 2)
    private BigDecimal humedadMinPct;

    @Column(name = "humedad_max_pct", precision = 5, scale = 2)
    private BigDecimal humedadMaxPct;

    @Column(name = "fecha_instalacion")
    private LocalDate fechaInstalacion;

    @Column(name = "activo")
    private boolean activo;
}