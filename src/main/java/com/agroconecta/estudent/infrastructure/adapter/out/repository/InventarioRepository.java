package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.InventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    boolean existsByCentroAcopioIdCentroAcopioAndProductoIdProducto(Long idCentroAcopio, Long idProducto);
    boolean existsByCentroAcopioIdCentroAcopioAndProductoIdProductoAndIdInventarioNot(
            Long idCentroAcopio, Long idProducto, Long idInventario);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from InventarioEntity i where i.idInventario = :id")
    Optional<InventarioEntity> findByIdForUpdate(@Param("id") Long id);
}