package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LecturaSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.OffsetDateTime;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensorEntity, Long> {

    Page<LecturaSensorEntity> findAllBySensor_CentroAcopio_IdCentroAcopioAndFechaLecturaGreaterThanEqualAndFechaLecturaLessThan(
            Long idCentroAcopio,
            OffsetDateTime desde,
            OffsetDateTime hastaExclusivo,
            Pageable pageable
    );

    @Query("""
           select l from LecturaSensorEntity l
           join fetch l.sensor
           order by l.fechaLectura desc
           """)
    List<LecturaSensorEntity> findAllConSensor();

    @Query("""
           select l from LecturaSensorEntity l
           join fetch l.sensor s
           where l.idLectura in (
               select max(l2.idLectura) from LecturaSensorEntity l2 group by l2.sensor.idSensor
           )
           """)
    List<LecturaSensorEntity> findUltimasLecturasPorSensor();
}