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
}