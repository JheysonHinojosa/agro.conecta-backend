package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.SensorIOTRequest;
import com.agroconecta.estudent.application.dto.response.SensorIOTResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.SensorIOTMapper;
import com.agroconecta.estudent.application.services.SensorIOTService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.SensorIotEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CentroAcopioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.SensorIOTRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class SensorIOTServiceImpl implements SensorIOTService {

    private final SensorIOTRepository sensorRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final SensorIOTMapper sensorMapper;

    @Override
    public SensorIOTResponse crear(SensorIOTRequest request) {

        String codigo = normalizarCodigo(request.codigoSensor());

        if (sensorRepository.existsByCodigoSensor(codigo)) {
            throw new IllegalArgumentException("El código de sensor ya está en uso");
        }

        SensorIotEntity entity = sensorMapper.toEntity(request);
        entity.setCodigoSensor(codigo);
        entity.setCentroAcopio(buscarCentroAcopio(request.idCentroAcopio()));

        return sensorMapper.toResponse(sensorRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SensorIOTResponse> listar() {
        return sensorRepository.findAllConCentroAcopio()
                .stream()
                .map(sensorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SensorIOTResponse buscarPorId(Long id) {
        return sensorMapper.toResponse(buscarSensor(id));
    }

    @Override
    public SensorIOTResponse actualizar(Long id, SensorIOTRequest request) {

        SensorIotEntity entity = buscarSensor(id);
        String codigo = normalizarCodigo(request.codigoSensor());

        if (sensorRepository.existsByCodigoSensorAndIdSensorNot(codigo, id)) {
            throw new IllegalArgumentException("El código de sensor ya está en uso");
        }

        sensorMapper.updateEntity(entity, request);
        entity.setCodigoSensor(codigo);
        entity.setCentroAcopio(buscarCentroAcopio(request.idCentroAcopio()));

        return sensorMapper.toResponse(sensorRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación lógica: el sensor está referenciado por lecturas y alertas
        SensorIotEntity entity = buscarSensor(id);
        entity.setActivo(false);
        sensorRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private SensorIotEntity buscarSensor(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor no encontrado"));
    }

    private CentroAcopioEntity buscarCentroAcopio(Long id) {
        CentroAcopioEntity centro = centroAcopioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
        if (!centro.isActivo()) {
            throw new IllegalArgumentException("El centro de acopio está inactivo");
        }
        return centro;
    }
}