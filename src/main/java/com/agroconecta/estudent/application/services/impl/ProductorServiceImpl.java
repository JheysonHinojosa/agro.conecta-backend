package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ProductorRequest;
import com.agroconecta.estudent.application.dto.response.ProductorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ProductorMapper;
import com.agroconecta.estudent.application.services.ProductorService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductorServiceImpl implements ProductorService {

    private final ProductorRepository productorRepository;
    private final ParTipoDocumentoRepository tipoDocumentoRepository;
    private final ParPaisRepository paisRepository;
    private final ParCategoriaProductoRepository categoriaRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final ProductorMapper productorMapper;

    @Override
    public ProductorResponse crear(ProductorRequest request) {

        if (productorRepository.existsByTipoDocumentoIdTipoDocumentoAndNroDocumento(
                request.idTipoDocumento(), request.nroDocumento())) {
            throw new IllegalArgumentException("Ya existe un productor con ese documento");
        }
        if (productorRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        ProductorEntity entity = productorMapper.toEntity(request);
        asignarRelaciones(entity, request);
        entity.setFechaRegistro(LocalDate.now());

        return productorMapper.toResponse(productorRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductorResponse> listar() {
        return productorRepository.findAllConRelaciones()
                .stream()
                .map(productorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductorResponse buscarPorId(Long id) {
        return productorMapper.toResponse(buscarProductor(id));
    }

    @Override
    public ProductorResponse actualizar(Long id, ProductorRequest request) {

        ProductorEntity entity = buscarProductor(id);

        if (productorRepository.existsByTipoDocumentoIdTipoDocumentoAndNroDocumentoAndIdProductorNot(
                request.idTipoDocumento(), request.nroDocumento(), id)) {
            throw new IllegalArgumentException("Ya existe un productor con ese documento");
        }
        if (productorRepository.existsByEmailAndIdProductorNot(request.email(), id)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        productorMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);

        return productorMapper.toResponse(productorRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación lógica: el productor está referenciado por productos
        ProductorEntity entity = buscarProductor(id);
        entity.setActivo(false);
        productorRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private void asignarRelaciones(ProductorEntity entity, ProductorRequest request) {
        entity.setTipoDocumento(buscarTipoDocumento(request.idTipoDocumento()));
        entity.setPais(buscarPais(request.idPais()));
        entity.setCategoriaPrincipal(buscarCategoria(request.idCategoriaPrincipal()));
        entity.setCentroAcopio(buscarCentroAcopio(request.idCentroAcopio()));
    }

    private ProductorEntity buscarProductor(Long id) {
        return productorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Productor no encontrado"));
    }

    private ParTipoDocumentoEntity buscarTipoDocumento(Integer id) {
        ParTipoDocumentoEntity tipo = tipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
        if (!tipo.isActivo()) {
            throw new IllegalArgumentException("El tipo de documento está inactivo");
        }
        return tipo;
    }

    private ParPaisEntity buscarPais(Integer id) {
        ParPaisEntity pais = paisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("País no encontrado"));
        if (!pais.isActivo()) {
            throw new IllegalArgumentException("El país está inactivo");
        }
        return pais;
    }

    private ParCategoriaProductoEntity buscarCategoria(Integer id) {
        ParCategoriaProductoEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de producto no encontrada"));
        if (!categoria.isActivo()) {
            throw new IllegalArgumentException("La categoría de producto está inactiva");
        }
        return categoria;
    }

    /** El centro de acopio es opcional. */
    private CentroAcopioEntity buscarCentroAcopio(Long id) {
        if (id == null) {
            return null;
        }
        CentroAcopioEntity centro = centroAcopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
        if (!centro.isActivo()) {
            throw new IllegalArgumentException("El centro de acopio está inactivo");
        }
        return centro;
    }
}