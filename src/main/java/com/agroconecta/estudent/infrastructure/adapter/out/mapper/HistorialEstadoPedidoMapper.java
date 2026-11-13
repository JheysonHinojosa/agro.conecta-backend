package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.HistorialEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.HistorialEstadoPedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.HistorialEstadoPedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HistorialEstadoPedidoMapper {

    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "estadoAnterior", ignore = true)
    @Mapping(target = "estadoNuevo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    HistorialEstadoPedidoEntity toEntity(HistorialEstadoPedidoRequest request);

    @Mapping(target = "idHistorial", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "estadoAnterior", ignore = true)
    @Mapping(target = "estadoNuevo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget HistorialEstadoPedidoEntity entity, HistorialEstadoPedidoRequest request);

    @Mapping(source = "pedido.idPedido", target = "idPedido")
    @Mapping(source = "estadoAnterior.idEstadoPedido", target = "idEstadoAnterior")
    @Mapping(source = "estadoAnterior.nombre", target = "nombreEstadoAnterior")
    @Mapping(source = "estadoNuevo.idEstadoPedido", target = "idEstadoNuevo")
    @Mapping(source = "estadoNuevo.nombre", target = "nombreEstadoNuevo")
    HistorialEstadoPedidoResponse toResponse(HistorialEstadoPedidoEntity entity);
}
