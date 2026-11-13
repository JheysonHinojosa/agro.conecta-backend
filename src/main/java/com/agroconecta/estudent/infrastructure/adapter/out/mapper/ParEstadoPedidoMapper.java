package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.ParEstadoPedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParEstadoPedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParEstadoPedidoMapper {

    @Mapping(target = "idEstadoPedido", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParEstadoPedidoEntity toEntity(ParEstadoPedidoRequest request);

    @Mapping(target = "idEstadoPedido", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParEstadoPedidoEntity entity, ParEstadoPedidoRequest request);

    ParEstadoPedidoResponse toResponse(ParEstadoPedidoEntity entity);
}