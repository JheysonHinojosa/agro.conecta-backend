package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParPaisRequest;
import com.agroconecta.estudent.application.dto.response.ParPaisResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParPaisEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParPaisMapper {

    @Mapping(target = "idPais", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParPaisEntity toEntity(ParPaisRequest request);

    @Mapping(target = "idPais", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParPaisEntity entity, ParPaisRequest request);

    @Mapping(source = "moneda.idMoneda", target = "idMoneda")
    @Mapping(source = "moneda.nombre", target = "nombreMoneda")
    ParPaisResponse toResponse(ParPaisEntity entity);
}
