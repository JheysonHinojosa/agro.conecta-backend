package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParRolRepository extends JpaRepository<ParRolEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdRolNot(String codigo, Integer idRol);
}