package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParTipoAlertaRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoAlertaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParTipoAlertaMapper;
import com.agroconecta.estudent.application.services.ParTipoAlertaService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoAlertaEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoAlertaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParTipoAlertaServiceImpl implements ParTipoAlertaService {

    private final ParTipoAlertaRepository tipoAlertaRepository;
    private final ParTipoAlertaMapper tipoAlertaMapper;

    @Override
    public ParTipoAlertaResponse crear(ParTipoAlertaRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (tipoAlertaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de tipo de alerta ya está en uso");
        }

        ParTipoAlertaEntity entity = tipoAlertaMapper.toEntity(request);
        entity.setCodigo(codigo);

        return tipoAlertaMapper.toResponse(tipoAlertaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParTipoAlertaResponse> listar() {
        return tipoAlertaRepository.findAll()
                .stream()
                .map(tipoAlertaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParTipoAlertaResponse buscarPorId(Integer id) {
        return tipoAlertaMapper.toResponse(buscarTipoAlerta(id));
    }

    @Override
    public ParTipoAlertaResponse actualizar(Integer id, ParTipoAlertaRequest request) {

        ParTipoAlertaEntity entity = buscarTipoAlerta(id);
        String codigo = normalizarCodigo(request.codigo());

        if (tipoAlertaRepository.existsByCodigoAndIdTipoAlertaNot(codigo, id)) {
            throw new IllegalArgumentException("El código de tipo de alerta ya está en uso");
        }

        tipoAlertaMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return tipoAlertaMapper.toResponse(tipoAlertaRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: el tipo de alerta está referenciado por alertas
        ParTipoAlertaEntity entity = buscarTipoAlerta(id);
        entity.setActivo(false);
        tipoAlertaRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParTipoAlertaEntity buscarTipoAlerta(Integer id) {
        return tipoAlertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de alerta no encontrado"));
    }
}