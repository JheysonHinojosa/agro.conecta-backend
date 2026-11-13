package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.CentroAcopioRequest;
import com.agroconecta.estudent.application.dto.response.CentroAcopioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CentroAcopioMapper {

    @Mapping(target = "idCentroAcopio", ignore = true)
    @Mapping(target = "pais", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CentroAcopioEntity toEntity(CentroAcopioRequest request);

    @Mapping(target = "idCentroAcopio", ignore = true)
    @Mapping(target = "pais", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget CentroAcopioEntity entity, CentroAcopioRequest request);

    @Mapping(source = "pais.idPais", target = "idPais")
    @Mapping(source = "pais.nombre", target = "nombrePais")
    CentroAcopioResponse toResponse(CentroAcopioEntity entity);
}
