package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndIdUsuarioNot(String username, Long idUsuario);
    boolean existsByEmailAndIdUsuarioNot(String email, Long idUsuario);

    @Query("select u from UsuarioEntity u left join fetch u.rol where lower(u.username) = lower(:username)")
    Optional<UsuarioEntity> findByUsername(@Param("username") String username);

    @Query("select u from UsuarioEntity u left join fetch u.rol order by u.idUsuario")
    java.util.List<UsuarioEntity> findAllConRol();
}