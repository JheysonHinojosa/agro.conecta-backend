package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParPaisRequest;
import com.agroconecta.estudent.application.dto.response.ParPaisResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParPaisMapper;
import com.agroconecta.estudent.application.services.ParPaisService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParMonedaEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParPaisEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParMonedaRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParPaisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParPaisServiceImpl implements ParPaisService {

    private final ParPaisRepository paisRepository;
    private final ParMonedaRepository monedaRepository;
    private final ParPaisMapper paisMapper;

    @Override
    public ParPaisResponse crear(ParPaisRequest request) {

        String codigoIso = normalizarCodigo(request.codigoIso());

        if (paisRepository.existsByCodigoIso(codigoIso)) {
            throw new IllegalArgumentException("El código ISO del país ya está en uso");
        }

        ParPaisEntity entity = paisMapper.toEntity(request);
        entity.setCodigoIso(codigoIso);
        entity.setMoneda(buscarMoneda(request.idMoneda()));

        return paisMapper.toResponse(paisRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParPaisResponse> listar() {
        return paisRepository.findAllConMoneda()
                .stream()
                .map(paisMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParPaisResponse buscarPorId(Integer id) {
        return paisMapper.toResponse(buscarPais(id));
    }

    @Override
    public ParPaisResponse actualizar(Integer id, ParPaisRequest request) {

        ParPaisEntity entity = buscarPais(id);
        String codigoIso = normalizarCodigo(request.codigoIso());

        if (paisRepository.existsByCodigoIsoAndIdPaisNot(codigoIso, id)) {
            throw new IllegalArgumentException("El código ISO del país ya está en uso");
        }

        paisMapper.updateEntity(entity, request);
        entity.setCodigoIso(codigoIso);
        entity.setMoneda(buscarMoneda(request.idMoneda()));

        return paisMapper.toResponse(paisRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: el país está referenciado por compradores, productores y centros de acopio
        ParPaisEntity entity = buscarPais(id);
        entity.setActivo(false);
        paisRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigoIso) {
        return codigoIso.trim().toUpperCase(Locale.ROOT);
    }

    private ParPaisEntity buscarPais(Integer id) {
        return paisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("País no encontrado"));
    }

    private ParMonedaEntity buscarMoneda(Integer id) {
        ParMonedaEntity moneda = monedaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moneda no encontrada"));
        if (!moneda.isActivo()) {
            throw new IllegalArgumentException("La moneda está inactiva");
        }
        return moneda;
    }
}