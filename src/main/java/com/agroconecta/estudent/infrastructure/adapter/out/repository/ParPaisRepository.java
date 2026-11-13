package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParPaisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParPaisRepository extends JpaRepository<ParPaisEntity, Integer> {

    boolean existsByCodigoIso(String codigoIso);

    boolean existsByCodigoIsoAndIdPaisNot(String codigoIso, Integer idPais);

    @Query("""
           select p from ParPaisEntity p
           join fetch p.moneda
           order by p.nombre
           """)
    List<ParPaisEntity> findAllConMoneda();
}