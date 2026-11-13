package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParTipoDocumentoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoDocumentoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParTipoDocumentoMapper;
import com.agroconecta.estudent.application.services.ParTipoDocumentoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoDocumentoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoDocumentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParTipoDocumentoServiceImpl implements ParTipoDocumentoService {

    private final ParTipoDocumentoRepository tipoDocumentoRepository;
    private final ParTipoDocumentoMapper tipoDocumentoMapper;

    @Override
    public ParTipoDocumentoResponse crear(ParTipoDocumentoRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (tipoDocumentoRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de tipo de documento ya está en uso");
        }

        ParTipoDocumentoEntity entity = tipoDocumentoMapper.toEntity(request);
        entity.setCodigo(codigo);

        return tipoDocumentoMapper.toResponse(tipoDocumentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParTipoDocumentoResponse> listar() {
        return tipoDocumentoRepository.findAll()
                .stream()
                .map(tipoDocumentoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParTipoDocumentoResponse buscarPorId(Integer id) {
        return tipoDocumentoMapper.toResponse(buscarTipoDocumento(id));
    }

    @Override
    public ParTipoDocumentoResponse actualizar(Integer id, ParTipoDocumentoRequest request) {

        ParTipoDocumentoEntity entity = buscarTipoDocumento(id);
        String codigo = normalizarCodigo(request.codigo());

        if (tipoDocumentoRepository.existsByCodigoAndIdTipoDocumentoNot(codigo, id)) {
            throw new IllegalArgumentException("El código de tipo de documento ya está en uso");
        }

        tipoDocumentoMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return tipoDocumentoMapper.toResponse(tipoDocumentoRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        ParTipoDocumentoEntity entity = buscarTipoDocumento(id);
        entity.setActivo(false);
        tipoDocumentoRepository.save(entity);
    }

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParTipoDocumentoEntity buscarTipoDocumento(Integer id) {
        return tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
    }
}