package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.UsuarioRequest;
import com.agroconecta.estudent.application.dto.response.UsuarioResponse;
import com.agroconecta.estudent.application.mapper.UsuarioMapper;
import com.agroconecta.estudent.application.services.UsuarioService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CompradorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParRolEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.UsuarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CompradorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParRolRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final int MIN_PASSWORD_LENGTH = 4;

    private final UsuarioRepository usuarioRepository;
    private final ParRolRepository parRolRepository;
    private final CompradorRepository compradorRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponse crear(UsuarioRequest request) {

        if (request.passwordHash() == null || request.passwordHash().isBlank()
                || request.passwordHash().length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    "La contraseña es obligatoria y debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres");
        }
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("El username ya está en uso");
        }
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        UsuarioEntity entity = usuarioMapper.toEntity(request);
        entity.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        entity.setRol(buscarRol(request.idRol()));
        entity.setComprador(buscarComprador(request.idComprador()));

        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return usuarioMapper.toResponse(buscarUsuario(id));
    }

    @Override
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {

        UsuarioEntity entity = buscarUsuario(id);

        if (usuarioRepository.existsByUsernameAndIdUsuarioNot(request.username(), id)) {
            throw new IllegalArgumentException("El username ya está en uso");
        }
        if (usuarioRepository.existsByEmailAndIdUsuarioNot(request.email(), id)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        usuarioMapper.updateEntity(entity, request);
        entity.setRol(buscarRol(request.idRol()));
        entity.setComprador(buscarComprador(request.idComprador()));

        // La contraseña solo se cambia si viene en la petición
        if (request.passwordHash() != null && !request.passwordHash().isBlank()) {
            if (request.passwordHash().length() < MIN_PASSWORD_LENGTH) {
                throw new IllegalArgumentException(
                        "La nueva contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres");
            }
            entity.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        }

        return usuarioMapper.toResponse(usuarioRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación lógica
        UsuarioEntity entity = buscarUsuario(id);
        entity.setActivo(false);
        usuarioRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private UsuarioEntity buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    private ParRolEntity buscarRol(Integer idRol) {
        return parRolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
    }

    private CompradorEntity buscarComprador(Long idComprador) {
        return compradorRepository.findById(idComprador)
                .orElseThrow(() -> new EntityNotFoundException("Comprador no encontrado"));
    }
}