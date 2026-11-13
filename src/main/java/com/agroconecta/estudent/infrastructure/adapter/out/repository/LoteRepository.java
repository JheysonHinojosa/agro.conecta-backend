package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoteRepository extends JpaRepository<LoteEntity, Long> {

    boolean existsByCodigoLote(String codigoLote);

    boolean existsByCodigoLoteAndIdLoteNot(String codigoLote, Long idLote);

    @Query("""
           select l from LoteEntity l
           join fetch l.producto
           join fetch l.centroAcopio
           """)
    List<LoteEntity> findAllConRelaciones();
}