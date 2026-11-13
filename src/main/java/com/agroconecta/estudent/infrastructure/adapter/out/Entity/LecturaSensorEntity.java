package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "lectura_sensor")
@Getter
@Setter
@NoArgsConstructor
public class LecturaSensorEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lectura")
    private Long idLectura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sensor")
    private SensorIotEntity sensor;

    @Column(name = "fecha_lectura")
    private OffsetDateTime fechaLectura;

    @Column(name = "temperatura_c", precision = 5, scale = 2)
    private BigDecimal temperaturaC;

    @Column(name = "humedad_pct", precision = 5, scale = 2)
    private BigDecimal humedadPct;
}