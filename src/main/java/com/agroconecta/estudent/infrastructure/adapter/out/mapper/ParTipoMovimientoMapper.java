package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParTipoMovimientoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoMovimientoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoMovimientoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParTipoMovimientoMapper {

    @Mapping(target = "idTipoMovimiento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParTipoMovimientoEntity toEntity(ParTipoMovimientoRequest request);

    @Mapping(target = "idTipoMovimiento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParTipoMovimientoEntity entity, ParTipoMovimientoRequest request);

    ParTipoMovimientoResponse toResponse(ParTipoMovimientoEntity entity);
}
