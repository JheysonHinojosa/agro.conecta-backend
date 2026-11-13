package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.CentroAcopioRequest;
import com.agroconecta.estudent.application.dto.response.CentroAcopioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.CentroAcopioMapper;
import com.agroconecta.estudent.application.services.CentroAcopioService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParPaisEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CentroAcopioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParPaisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CentroAcopioServiceImpl implements CentroAcopioService {

    private final CentroAcopioRepository centroAcopioRepository;
    private final ParPaisRepository parPaisRepository;
    private final CentroAcopioMapper centroAcopioMapper;

    @Override
    public CentroAcopioResponse crear(CentroAcopioRequest request) {

        if (centroAcopioRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("El código de centro de acopio ya está en uso");
        }

        CentroAcopioEntity entity = centroAcopioMapper.toEntity(request);
        entity.setPais(buscarPais(request.idPais()));

        return centroAcopioMapper.toResponse(centroAcopioRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroAcopioResponse> listar() {
        return centroAcopioRepository.findAll()
                .stream()
                .map(centroAcopioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CentroAcopioResponse buscarPorId(Long id) {
        return centroAcopioMapper.toResponse(buscarCentroAcopio(id));
    }

    @Override
    public CentroAcopioResponse actualizar(Long id, CentroAcopioRequest request) {

        CentroAcopioEntity entity = buscarCentroAcopio(id);

        if (centroAcopioRepository.existsByCodigoAndIdCentroAcopioNot(request.codigo(), id)) {
            throw new IllegalArgumentException("El código de centro de acopio ya está en uso");
        }

        centroAcopioMapper.updateEntity(entity, request);
        entity.setPais(buscarPais(request.idPais()));

        return centroAcopioMapper.toResponse(centroAcopioRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        CentroAcopioEntity entity = buscarCentroAcopio(id);
        entity.setActivo(false);
        centroAcopioRepository.save(entity);
    }

    private CentroAcopioEntity buscarCentroAcopio(Long id) {
        return centroAcopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
    }

    private ParPaisEntity buscarPais(Integer idPais) {
        return parPaisRepository.findById(idPais)
                .orElseThrow(() -> new EntityNotFoundException("País no encontrado"));
    }
}