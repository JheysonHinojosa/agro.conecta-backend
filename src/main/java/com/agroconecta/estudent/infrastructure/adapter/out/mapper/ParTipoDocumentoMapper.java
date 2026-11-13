package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParTipoDocumentoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoDocumentoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoDocumentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParTipoDocumentoMapper {

    @Mapping(target = "idTipoDocumento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParTipoDocumentoEntity toEntity(ParTipoDocumentoRequest request);

    @Mapping(target = "idTipoDocumento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParTipoDocumentoEntity entity, ParTipoDocumentoRequest request);

    ParTipoDocumentoResponse toResponse(ParTipoDocumentoEntity entity);
}
