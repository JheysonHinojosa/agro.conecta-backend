package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.InventarioRequest;
import com.agroconecta.estudent.application.dto.response.InventarioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.InventarioMapper;
import com.agroconecta.estudent.application.services.InventarioService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.InventarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CentroAcopioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.InventarioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final ProductoRepository productoRepository;
    private final InventarioMapper inventarioMapper;

    @Override
    public InventarioResponse crear(InventarioRequest request) {

        if (inventarioRepository.existsByCentroAcopioIdCentroAcopioAndProductoIdProducto(
                request.idCentroAcopio(), request.idProducto())) {
            throw new IllegalArgumentException(
                    "Ya existe un inventario de ese producto en el centro de acopio");
        }

        InventarioEntity entity = inventarioMapper.toEntity(request);
        asignarRelaciones(entity, request);

        return inventarioMapper.toResponse(inventarioRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioResponse> listar() {
        return inventarioRepository.findAll()
                .stream()
                .map(inventarioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioResponse buscarPorId(Long id) {
        return inventarioMapper.toResponse(buscarInventario(id));
    }

    @Override
    public InventarioResponse actualizar(Long id, InventarioRequest request) {

        InventarioEntity entity = buscarInventario(id);

        if (inventarioRepository.existsByCentroAcopioIdCentroAcopioAndProductoIdProductoAndIdInventarioNot(
                request.idCentroAcopio(), request.idProducto(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe un inventario de ese producto en el centro de acopio");
        }

        inventarioMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);

        return inventarioMapper.toResponse(inventarioRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // La tabla inventario no tiene columna "activo", por eso el borrado es físico
        inventarioRepository.delete(buscarInventario(id));
    }

    // ---------- métodos privados ----------

    private void asignarRelaciones(InventarioEntity entity, InventarioRequest request) {
        entity.setCentroAcopio(buscarCentroAcopio(request.idCentroAcopio()));
        entity.setProducto(buscarProducto(request.idProducto()));
    }

    private InventarioEntity buscarInventario(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));
    }

    private CentroAcopioEntity buscarCentroAcopio(Long id) {
        return centroAcopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
    }

    private ProductoEntity buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }
}