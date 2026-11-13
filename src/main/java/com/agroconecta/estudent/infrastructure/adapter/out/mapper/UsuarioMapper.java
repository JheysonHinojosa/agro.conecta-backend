package com.agroconecta.estudent.application.mapper;

import com.agroconecta.estudent.application.dto.request.UsuarioRequest;
import com.agroconecta.estudent.application.dto.response.UsuarioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "comprador", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    UsuarioEntity toEntity(UsuarioRequest request);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "comprador", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget UsuarioEntity entity, UsuarioRequest request);

    @Mapping(source = "rol.idRol", target = "idRol")
    @Mapping(source = "rol.nombre", target = "nombreRol")
    @Mapping(source = "comprador.idComprador", target = "idComprador")
    UsuarioResponse toResponse(UsuarioEntity entity);
}