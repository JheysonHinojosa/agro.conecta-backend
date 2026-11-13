package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParUnidadMedidaRequest;
import com.agroconecta.estudent.application.dto.response.ParUnidadMedidaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParUnidadMedidaMapper;
import com.agroconecta.estudent.application.services.ParUnidadMedidaService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParUnidadMedidaEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParUnidadMedidaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParUnidadMedidaServiceImpl implements ParUnidadMedidaService {

    private final ParUnidadMedidaRepository unidadMedidaRepository;
    private final ParUnidadMedidaMapper unidadMedidaMapper;

    @Override
    public ParUnidadMedidaResponse crear(ParUnidadMedidaRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (unidadMedidaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de unidad de medida ya está en uso");
        }

        ParUnidadMedidaEntity entity = unidadMedidaMapper.toEntity(request);
        entity.setCodigo(codigo);

        return unidadMedidaMapper.toResponse(unidadMedidaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParUnidadMedidaResponse> listar() {
        return unidadMedidaRepository.findAll()
                .stream()
                .map(unidadMedidaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParUnidadMedidaResponse buscarPorId(Integer id) {
        return unidadMedidaMapper.toResponse(buscarUnidadMedida(id));
    }

    @Override
    public ParUnidadMedidaResponse actualizar(Integer id, ParUnidadMedidaRequest request) {

        ParUnidadMedidaEntity entity = buscarUnidadMedida(id);
        String codigo = normalizarCodigo(request.codigo());

        if (unidadMedidaRepository.existsByCodigoAndIdUnidadMedidaNot(codigo, id)) {
            throw new IllegalArgumentException("El código de unidad de medida ya está en uso");
        }

        unidadMedidaMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return unidadMedidaMapper.toResponse(unidadMedidaRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: la unidad de medida está referenciada por productos
        ParUnidadMedidaEntity entity = buscarUnidadMedida(id);
        entity.setActivo(false);
        unidadMedidaRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParUnidadMedidaEntity buscarUnidadMedida(Integer id) {
        return unidadMedidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
    }
}