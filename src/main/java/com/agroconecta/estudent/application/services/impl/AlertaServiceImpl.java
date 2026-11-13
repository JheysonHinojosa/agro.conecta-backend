package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.AlertaRequest;
import com.agroconecta.estudent.application.dto.response.AlertaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.AlertaMapper;
import com.agroconecta.estudent.application.services.AlertaService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepository alertaRepository;
    private final ParTipoAlertaRepository parTipoAlertaRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final ProductoRepository productoRepository;
    private final SensorIOTRepository sensorIotRepository;
    private final LecturaSensorRepository lecturaSensorRepository;
    private final AlertaMapper alertaMapper;

    @Override
    public AlertaResponse crear(AlertaRequest request) {

        AlertaEntity entity = alertaMapper.toEntity(request);
        asignarRelaciones(entity, request);

        if (entity.getFechaAlerta() == null) {
            entity.setFechaAlerta(OffsetDateTime.now());
        }
        aplicarAtencion(entity, request);

        return alertaMapper.toResponse(alertaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlertaResponse> listar() {
        return alertaRepository.findAll()
                .stream()
                .map(alertaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlertaResponse buscarPorId(Long id) {
        return alertaMapper.toResponse(buscarAlerta(id));
    }

    @Override
    public AlertaResponse actualizar(Long id, AlertaRequest request) {

        AlertaEntity entity = buscarAlerta(id);

        alertaMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);
        aplicarAtencion(entity, request);

        return alertaMapper.toResponse(alertaRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        alertaRepository.delete(buscarAlerta(id));
    }

    private void asignarRelaciones(AlertaEntity entity, AlertaRequest request) {
        entity.setTipoAlerta(buscarTipoAlerta(request.idTipoAlerta()));
        entity.setCentroAcopio(buscarCentroAcopio(request.idCentroAcopio()));
        entity.setProducto(buscarProducto(request.idProducto()));
        entity.setSensor(buscarSensor(request.idSensor()));
        entity.setLectura(buscarLectura(request.idLectura()));
    }

    private void aplicarAtencion(AlertaEntity entity, AlertaRequest request) {
        if (request.atendida() == null) {
            return;
        }
        entity.setAtendida(request.atendida());

        if (request.atendida()) {
            if (entity.getFechaAtencion() == null) {
                entity.setFechaAtencion(OffsetDateTime.now());
            }
        } else {
            entity.setFechaAtencion(null);
            entity.setAtendidaPor(null);
        }
    }

    private AlertaEntity buscarAlerta(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alerta no encontrada"));
    }

    private ParTipoAlertaEntity buscarTipoAlerta(Integer id) {
        return parTipoAlertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de alerta no encontrado"));
    }

    private CentroAcopioEntity buscarCentroAcopio(Long id) {
        return centroAcopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
    }

    private ProductoEntity buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    private SensorIotEntity buscarSensor(Long id) {
        return sensorIotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor no encontrado"));
    }

    private LecturaSensorEntity buscarLectura(Long id) {
        return lecturaSensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lectura no encontrada"));
    }
}
