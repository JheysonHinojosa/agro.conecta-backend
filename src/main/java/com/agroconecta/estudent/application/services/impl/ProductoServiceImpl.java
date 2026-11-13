package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ProductoRequest;
import com.agroconecta.estudent.application.dto.response.ProductoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ProductoMapper;
import com.agroconecta.estudent.application.services.ProductoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ParCategoriaProductoRepository categoriaRepository;
    private final ParUnidadMedidaRepository unidadMedidaRepository;
    private final ParMonedaRepository monedaRepository;
    private final ProductorRepository productorRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponse crear(ProductoRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (productoRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de producto ya está en uso");
        }

        ProductoEntity entity = productoMapper.toEntity(request);
        entity.setCodigo(codigo);
        asignarRelaciones(entity, request);

        return productoMapper.toResponse(productoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return productoRepository.findAllConRelaciones()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse buscarPorId(Long id) {
        return productoMapper.toResponse(buscarProducto(id));
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {

        ProductoEntity entity = buscarProducto(id);
        String codigo = normalizarCodigo(request.codigo());

        if (productoRepository.existsByCodigoAndIdProductoNot(codigo, id)) {
            throw new IllegalArgumentException("El código de producto ya está en uso");
        }

        productoMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);
        asignarRelaciones(entity, request);

        return productoMapper.toResponse(productoRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación lógica: el producto está referenciado por lotes, inventario, detalles y alertas
        ProductoEntity entity = buscarProducto(id);
        entity.setActivo(false);
        productoRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private void asignarRelaciones(ProductoEntity entity, ProductoRequest request) {
        entity.setCategoria(buscarCategoria(request.idCategoria()));
        entity.setUnidadMedida(buscarUnidadMedida(request.idUnidadMedida()));
        entity.setMoneda(buscarMoneda(request.idMoneda()));
        entity.setProductor(buscarProductor(request.idProductor()));
    }

    private ProductoEntity buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    private ParCategoriaProductoEntity buscarCategoria(Integer id) {
        ParCategoriaProductoEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría de producto no encontrada"));
        if (!categoria.isActivo()) {
            throw new IllegalArgumentException("La categoría de producto está inactiva");
        }
        return categoria;
    }

    private ParUnidadMedidaEntity buscarUnidadMedida(Integer id) {
        ParUnidadMedidaEntity unidad = unidadMedidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidad de medida no encontrada"));
        if (!unidad.isActivo()) {
            throw new IllegalArgumentException("La unidad de medida está inactiva");
        }
        return unidad;
    }

    private ParMonedaEntity buscarMoneda(Integer id) {
        ParMonedaEntity moneda = monedaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moneda no encontrada"));
        if (!moneda.isActivo()) {
            throw new IllegalArgumentException("La moneda está inactiva");
        }
        return moneda;
    }

    private ProductorEntity buscarProductor(Long id) {
        ProductorEntity productor = productorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Productor no encontrado"));
        if (!productor.isActivo()) {
            throw new IllegalArgumentException("El productor está inactivo");
        }
        return productor;
    }
}