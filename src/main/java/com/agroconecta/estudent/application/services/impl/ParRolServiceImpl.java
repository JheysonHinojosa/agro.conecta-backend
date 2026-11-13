package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParRolRequest;
import com.agroconecta.estudent.application.dto.response.ParRolResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParRolMapper;
import com.agroconecta.estudent.application.services.ParRolService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParRolEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParRolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParRolServiceImpl implements ParRolService {

    private final ParRolRepository rolRepository;
    private final ParRolMapper rolMapper;

    @Override
    public ParRolResponse crear(ParRolRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (rolRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de rol ya está en uso");
        }

        ParRolEntity entity = rolMapper.toEntity(request);
        entity.setCodigo(codigo);

        return rolMapper.toResponse(rolRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParRolResponse> listar() {
        return rolRepository.findAll()
                .stream()
                .map(rolMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParRolResponse buscarPorId(Integer id) {
        return rolMapper.toResponse(buscarRol(id));
    }

    @Override
    public ParRolResponse actualizar(Integer id, ParRolRequest request) {

        ParRolEntity entity = buscarRol(id);
        String codigo = normalizarCodigo(request.codigo());

        if (rolRepository.existsByCodigoAndIdRolNot(codigo, id)) {
            throw new IllegalArgumentException("El código de rol ya está en uso");
        }

        rolMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return rolMapper.toResponse(rolRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        ParRolEntity entity = buscarRol(id);
        entity.setActivo(false);
        rolRepository.save(entity);
    }

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParRolEntity buscarRol(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
    }
}