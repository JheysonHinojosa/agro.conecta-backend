package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CentroAcopioRepository extends JpaRepository<CentroAcopioEntity, Long> {
    boolean existsByCodigo(String codigo);
    boolean existsByCodigoAndIdCentroAcopioNot(String codigo, Long idCentroAcopio);
}