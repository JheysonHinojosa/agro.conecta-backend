package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CompradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompradorRepository extends JpaRepository<CompradorEntity, Long> {
    boolean existsByTipoDocumentoIdTipoDocumentoAndNroDocumento(Integer idTipoDocumento, String nroDocumento);
    boolean existsByTipoDocumentoIdTipoDocumentoAndNroDocumentoAndIdCompradorNot(
            Integer idTipoDocumento, String nroDocumento, Long idComprador);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdCompradorNot(String email, Long idComprador);

    @Query("""
           select c from CompradorEntity c
           join fetch c.tipoDocumento
           join fetch c.tipoComprador
           join fetch c.pais
           order by c.nombreRazonSocial
           """)
    List<CompradorEntity> findAllConRelaciones();
}