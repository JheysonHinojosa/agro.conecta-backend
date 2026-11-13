package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParCategoriaProductoRequest;
import com.agroconecta.estudent.application.dto.response.ParCategoriaProductoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParCategoriaProductoMapper;
import com.agroconecta.estudent.application.services.ParCategoriaProductoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParCategoriaProductoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParCategoriaProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParCategoriaProductoServiceImpl implements ParCategoriaProductoService {

    private final ParCategoriaProductoRepository categoriaRepository;
    private final ParCategoriaProductoMapper categoriaMapper;

    @Override
    public ParCategoriaProductoResponse crear(ParCategoriaProductoRequest request) {

        if (categoriaRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("El código de categoría ya está en uso");
        }

        ParCategoriaProductoEntity entity = categoriaMapper.toEntity(request);

        return categoriaMapper.toResponse(categoriaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParCategoriaProductoResponse> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParCategoriaProductoResponse buscarPorId(Integer id) {
        return categoriaMapper.toResponse(buscarCategoria(id));
    }

    @Override
    public ParCategoriaProductoResponse actualizar(Integer id, ParCategoriaProductoRequest request) {

        ParCategoriaProductoEntity entity = buscarCategoria(id);

        if (categoriaRepository.existsByCodigoAndIdCategoriaNot(request.codigo(), id)) {
            throw new IllegalArgumentException("El código de categoría ya está en uso");
        }

        categoriaMapper.updateEntity(entity, request);

        return categoriaMapper.toResponse(categoriaRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: la categoría está referenciada por productos y productores
        ParCategoriaProductoEntity entity = buscarCategoria(id);
        entity.setActivo(false);
        categoriaRepository.save(entity);
    }

    private ParCategoriaProductoEntity buscarCategoria(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de producto no encontrada"));
    }
}
