package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParMonedaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParMonedaRepository extends JpaRepository<ParMonedaEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdMonedaNot(String codigo, Integer idMoneda);
}
