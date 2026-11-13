package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.LoteRequest;
import com.agroconecta.estudent.application.dto.response.LoteResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.LoteMapper;
import com.agroconecta.estudent.application.services.LoteService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CentroAcopioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LoteRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoteServiceImpl implements LoteService {

    private final LoteRepository loteRepository;
    private final ProductoRepository productoRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final LoteMapper loteMapper;

    @Override
    public LoteResponse crear(LoteRequest request) {

        validarFechas(request);

        if (loteRepository.existsByCodigoLote(request.codigoLote())) {
            throw new IllegalArgumentException("El código de lote ya está en uso");
        }

        LoteEntity entity = loteMapper.toEntity(request);
        asignarRelaciones(entity, request);

        return loteMapper.toResponse(loteRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoteResponse> listar() {
        return loteRepository.findAllConRelaciones()
                .stream()
                .map(loteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LoteResponse buscarPorId(Long id) {
        return loteMapper.toResponse(buscarLote(id));
    }

    @Override
    public LoteResponse actualizar(Long id, LoteRequest request) {

        validarFechas(request);

        LoteEntity entity = buscarLote(id);

        if (loteRepository.existsByCodigoLoteAndIdLoteNot(request.codigoLote(), id)) {
            throw new IllegalArgumentException("El código de lote ya está en uso");
        }

        loteMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);

        return loteMapper.toResponse(loteRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación lógica: la tabla tiene la columna "activo" y el lote
        // está referenciado por detalles de pedido, movimientos y reseñas
        LoteEntity entity = buscarLote(id);
        entity.setActivo(false);
        loteRepository.save(entity);
    }

    private void validarFechas(LoteRequest request) {
        if (request.fechaCosecha().isAfter(request.fechaIngreso())) {
            throw new IllegalArgumentException(
                    "La fecha de cosecha no puede ser posterior a la fecha de ingreso");
        }
        if (request.fechaVencimiento().isBefore(request.fechaIngreso())) {
            throw new IllegalArgumentException(
                    "La fecha de vencimiento no puede ser anterior a la fecha de ingreso");
        }
    }

    private void asignarRelaciones(LoteEntity entity, LoteRequest request) {
        entity.setProducto(buscarProducto(request.idProducto()));
        entity.setCentroAcopio(buscarCentroAcopio(request.idCentroAcopio()));
    }

    private LoteEntity buscarLote(Long id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
    }

    private ProductoEntity buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    private CentroAcopioEntity buscarCentroAcopio(Long id) {
        return centroAcopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
    }
}