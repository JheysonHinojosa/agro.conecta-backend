package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.CompradorRequest;
import com.agroconecta.estudent.application.dto.response.CompradorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CompradorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompradorMapper {

    @Mapping(target = "idComprador", ignore = true)
    @Mapping(target = "tipoComprador", ignore = true)
    @Mapping(target = "tipoDocumento", ignore = true)
    @Mapping(target = "pais", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CompradorEntity toEntity(CompradorRequest request);

    @Mapping(target = "idComprador", ignore = true)
    @Mapping(target = "tipoComprador", ignore = true)
    @Mapping(target = "tipoDocumento", ignore = true)
    @Mapping(target = "pais", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget CompradorEntity entity, CompradorRequest request);

    @Mapping(source = "tipoComprador.idTipoComprador", target = "idTipoComprador")
    @Mapping(source = "tipoComprador.nombre", target = "nombreTipoComprador")
    @Mapping(source = "tipoDocumento.idTipoDocumento", target = "idTipoDocumento")
    @Mapping(source = "tipoDocumento.nombre", target = "nombreTipoDocumento")
    @Mapping(source = "pais.idPais", target = "idPais")
    @Mapping(source = "pais.nombre", target = "nombrePais")
    CompradorResponse toResponse(CompradorEntity entity);
}
