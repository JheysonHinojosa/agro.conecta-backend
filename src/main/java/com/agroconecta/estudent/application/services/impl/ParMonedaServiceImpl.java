package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParMonedaRequest;
import com.agroconecta.estudent.application.dto.response.ParMonedaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParMonedaMapper;
import com.agroconecta.estudent.application.services.ParMonedaService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParMonedaEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParMonedaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ParMonedaServiceImpl implements ParMonedaService {

    private final ParMonedaRepository monedaRepository;
    private final ParMonedaMapper monedaMapper;

    @Override
    public ParMonedaResponse crear(ParMonedaRequest request) {

        String codigo = normalizarCodigo(request.codigo());

        if (monedaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("El código de moneda ya está en uso");
        }

        ParMonedaEntity entity = monedaMapper.toEntity(request);
        entity.setCodigo(codigo);

        return monedaMapper.toResponse(monedaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParMonedaResponse> listar() {
        return monedaRepository.findAll()
                .stream()
                .map(monedaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParMonedaResponse buscarPorId(Integer id) {
        return monedaMapper.toResponse(buscarMoneda(id));
    }

    @Override
    public ParMonedaResponse actualizar(Integer id, ParMonedaRequest request) {

        ParMonedaEntity entity = buscarMoneda(id);
        String codigo = normalizarCodigo(request.codigo());

        if (monedaRepository.existsByCodigoAndIdMonedaNot(codigo, id)) {
            throw new IllegalArgumentException("El código de moneda ya está en uso");
        }

        monedaMapper.updateEntity(entity, request);
        entity.setCodigo(codigo);

        return monedaMapper.toResponse(monedaRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: la moneda está referenciada por países, productos y pedidos
        ParMonedaEntity entity = buscarMoneda(id);
        entity.setActivo(false);
        monedaRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    private ParMonedaEntity buscarMoneda(Integer id) {
        return monedaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moneda no encontrada"));
    }
}
