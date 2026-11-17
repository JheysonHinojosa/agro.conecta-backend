package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LecturaSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensorEntity, Long> {

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