package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductorRepository extends JpaRepository<ProductorEntity, Long> {

    boolean existsByTipoDocumentoIdTipoDocumentoAndNroDocumento(Integer idTipoDocumento, String nroDocumento);

    boolean existsByTipoDocumentoIdTipoDocumentoAndNroDocumentoAndIdProductorNot(
            Integer idTipoDocumento, String nroDocumento, Long idProductor);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdProductorNot(String email, Long idProductor);

    @Query("""
           select p from ProductorEntity p
           join fetch p.tipoDocumento
           join fetch p.pais
           join fetch p.categoriaPrincipal
           left join fetch p.centroAcopio
           order by p.nombreRazonSocial
           """)
    List<ProductorEntity> findAllConRelaciones();
}