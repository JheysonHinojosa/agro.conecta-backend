package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParTipoMovimientoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoMovimientoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParTipoMovimientoMapper;
import com.agroconecta.estudent.application.services.ParTipoMovimientoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoMovimientoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoMovimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParTipoMovimientoServiceImpl implements ParTipoMovimientoService {

    private final ParTipoMovimientoRepository tipoMovimientoRepository;
    private final ParTipoMovimientoMapper tipoMovimientoMapper;

    @Override
    public ParTipoMovimientoResponse crear(ParTipoMovimientoRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (tipoMovimientoRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de tipo de movimiento ya está en uso");
        }

        ParTipoMovimientoEntity entity = tipoMovimientoMapper.toEntity(request);
        entity.setCodigo(codigo);

        return tipoMovimientoMapper.toResponse(tipoMovimientoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParTipoMovimientoResponse> listar() {
        return tipoMovimientoRepository.findAll()
                .stream()
                .map(tipoMovimientoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParTipoMovimientoResponse buscarPorId(Integer id) {
        return tipoMovimientoMapper.toResponse(buscarTipoMovimiento(id));
    }

    @Override
    public ParTipoMovimientoResponse actualizar(Integer id, ParTipoMovimientoRequest request) {

        ParTipoMovimientoEntity entity = buscarTipoMovimiento(id);
        String codigo = normalizarCodigo(request.codigo());

        if (tipoMovimientoRepository.existsByCodigoAndIdTipoMovimientoNot(codigo, id)) {
            throw new IllegalArgumentException("El código de tipo de movimiento ya está en uso");
        }

        tipoMovimientoMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return tipoMovimientoMapper.toResponse(tipoMovimientoRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: el tipo está referenciado por movimientos de inventario
        ParTipoMovimientoEntity entity = buscarTipoMovimiento(id);
        entity.setActivo(false);
        tipoMovimientoRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParTipoMovimientoEntity buscarTipoMovimiento(Integer id) {
        return tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de movimiento no encontrado"));
    }
}