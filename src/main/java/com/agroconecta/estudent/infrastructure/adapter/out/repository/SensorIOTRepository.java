package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.SensorIotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorIOTRepository extends JpaRepository<SensorIotEntity, Long> {

    boolean existsByCodigoSensor(String codigoSensor);

    boolean existsByCodigoSensorAndIdSensorNot(String codigoSensor, Long idSensor);

    @Query("""
           select s from SensorIotEntity s
           join fetch s.centroAcopio
           order by s.codigoSensor
           """)
    List<SensorIotEntity> findAllConCentroAcopio();
}