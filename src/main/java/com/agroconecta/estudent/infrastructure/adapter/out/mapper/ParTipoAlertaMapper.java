package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParTipoAlertaRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoAlertaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoAlertaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParTipoAlertaMapper {

    @Mapping(target = "idTipoAlerta", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParTipoAlertaEntity toEntity(ParTipoAlertaRequest request);

    @Mapping(target = "idTipoAlerta", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParTipoAlertaEntity entity, ParTipoAlertaRequest request);

    ParTipoAlertaResponse toResponse(ParTipoAlertaEntity entity);
}
