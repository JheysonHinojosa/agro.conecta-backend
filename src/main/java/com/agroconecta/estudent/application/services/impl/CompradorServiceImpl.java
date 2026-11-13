package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.CompradorRequest;
import com.agroconecta.estudent.application.dto.response.CompradorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.CompradorMapper;
import com.agroconecta.estudent.application.services.CompradorService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CompradorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParPaisEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoCompradorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoDocumentoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CompradorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParPaisRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoCompradorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoDocumentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompradorServiceImpl implements CompradorService {

    private final CompradorRepository compradorRepository;
    private final ParTipoCompradorRepository parTipoCompradorRepository;
    private final ParTipoDocumentoRepository parTipoDocumentoRepository;
    private final ParPaisRepository parPaisRepository;
    private final CompradorMapper compradorMapper;

    @Override
    public CompradorResponse crear(CompradorRequest request) {

        if (compradorRepository.existsByTipoDocumentoIdTipoDocumentoAndNroDocumento(
                request.idTipoDocumento(), request.nroDocumento())) {
            throw new IllegalArgumentException("Ya existe un comprador con ese documento");
        }
        if (compradorRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        CompradorEntity entity = compradorMapper.toEntity(request);
        asignarRelaciones(entity, request);

        return compradorMapper.toResponse(compradorRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompradorResponse> listar() {
        return compradorRepository.findAll()
                .stream()
                .map(compradorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompradorResponse buscarPorId(Long id) {
        return compradorMapper.toResponse(buscarComprador(id));
    }

    @Override
    public CompradorResponse actualizar(Long id, CompradorRequest request) {

        CompradorEntity entity = buscarComprador(id);

        if (compradorRepository.existsByTipoDocumentoIdTipoDocumentoAndNroDocumentoAndIdCompradorNot(
                request.idTipoDocumento(), request.nroDocumento(), id)) {
            throw new IllegalArgumentException("Ya existe un comprador con ese documento");
        }
        if (compradorRepository.existsByEmailAndIdCompradorNot(request.email(), id)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        compradorMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);

        return compradorMapper.toResponse(compradorRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // Eliminación lógica: la tabla tiene la columna "activo"
        CompradorEntity entity = buscarComprador(id);
        entity.setActivo(false);
        compradorRepository.save(entity);
    }

    private void asignarRelaciones(CompradorEntity entity, CompradorRequest request) {
        entity.setTipoComprador(buscarTipoComprador(request.idTipoComprador()));
        entity.setTipoDocumento(buscarTipoDocumento(request.idTipoDocumento()));
        entity.setPais(buscarPais(request.idPais()));
    }

    private CompradorEntity buscarComprador(Long id) {
        return compradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprador no encontrado"));
    }

    private ParTipoCompradorEntity buscarTipoComprador(Integer id) {
        return parTipoCompradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de comprador no encontrado"));
    }

    private ParTipoDocumentoEntity buscarTipoDocumento(Integer id) {
        return parTipoDocumentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
    }

    private ParPaisEntity buscarPais(Integer id) {
        return parPaisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("País no encontrado"));
    }
}
