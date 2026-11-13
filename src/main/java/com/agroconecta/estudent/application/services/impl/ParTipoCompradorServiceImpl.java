package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParTipoCompradorRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoCompradorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParTipoCompradorMapper;
import com.agroconecta.estudent.application.services.ParTipoCompradorService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoCompradorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoCompradorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParTipoCompradorServiceImpl implements ParTipoCompradorService {

    private final ParTipoCompradorRepository tipoCompradorRepository;
    private final ParTipoCompradorMapper tipoCompradorMapper;

    @Override
    public ParTipoCompradorResponse crear(ParTipoCompradorRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (tipoCompradorRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de tipo de comprador ya está en uso");
        }

        ParTipoCompradorEntity entity = tipoCompradorMapper.toEntity(request);
        entity.setCodigo(codigo);

        return tipoCompradorMapper.toResponse(tipoCompradorRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParTipoCompradorResponse> listar() {
        return tipoCompradorRepository.findAll()
                .stream()
                .map(tipoCompradorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParTipoCompradorResponse buscarPorId(Integer id) {
        return tipoCompradorMapper.toResponse(buscarTipoComprador(id));
    }

    @Override
    public ParTipoCompradorResponse actualizar(Integer id, ParTipoCompradorRequest request) {

        ParTipoCompradorEntity entity = buscarTipoComprador(id);
        String codigo = normalizarCodigo(request.codigo());

        if (tipoCompradorRepository.existsByCodigoAndIdTipoCompradorNot(codigo, id)) {
            throw new IllegalArgumentException("El código de tipo de comprador ya está en uso");
        }

        tipoCompradorMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return tipoCompradorMapper.toResponse(tipoCompradorRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: el tipo de comprador está referenciado por compradores
        ParTipoCompradorEntity entity = buscarTipoComprador(id);
        entity.setActivo(false);
        tipoCompradorRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParTipoCompradorEntity buscarTipoComprador(Integer id) {
        return tipoCompradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de comprador no encontrado"));
    }
}