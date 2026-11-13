package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.LecturaSensorRequest;
import com.agroconecta.estudent.application.dto.response.LecturaSensorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.LecturaSensorMapper;
import com.agroconecta.estudent.application.services.LecturaSensorService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LecturaSensorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.SensorIotEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LecturaSensorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.SensorIOTRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LecturaSensorServiceImpl implements LecturaSensorService {

    private final LecturaSensorRepository lecturaSensorRepository;
    private final SensorIOTRepository sensorIotRepository;
    private final LecturaSensorMapper lecturaSensorMapper;

    @Override
    public LecturaSensorResponse crear(LecturaSensorRequest request) {

        SensorIotEntity sensor = buscarSensor(request.idSensor());
        if (!sensor.isActivo()) {
            throw new IllegalArgumentException("El sensor está inactivo y no puede registrar lecturas");
        }

        LecturaSensorEntity entity = lecturaSensorMapper.toEntity(request);
        entity.setSensor(sensor);

        if (entity.getFechaLectura() == null) {
            entity.setFechaLectura(OffsetDateTime.now());
        }

        return lecturaSensorMapper.toResponse(lecturaSensorRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensorResponse> listar() {
        return lecturaSensorRepository.findAllConSensor()
                .stream()
                .map(lecturaSensorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LecturaSensorResponse buscarPorId(Long id) {
        return lecturaSensorMapper.toResponse(buscarLectura(id));
    }

    @Override
    public LecturaSensorResponse actualizar(Long id, LecturaSensorRequest request) {

        LecturaSensorEntity entity = buscarLectura(id);

        lecturaSensorMapper.updateEntity(entity, request);
        entity.setSensor(buscarSensor(request.idSensor()));

        return lecturaSensorMapper.toResponse(lecturaSensorRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // La tabla lectura_sensor no tiene columna "activo", por eso el borrado es físico
        lecturaSensorRepository.delete(buscarLectura(id));
    }

    // ---------- métodos privados ----------

    private LecturaSensorEntity buscarLectura(Long id) {
        return lecturaSensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lectura de sensor no encontrada"));
    }

    private SensorIotEntity buscarSensor(Long id) {
        return sensorIotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor no encontrado"));
    }
}