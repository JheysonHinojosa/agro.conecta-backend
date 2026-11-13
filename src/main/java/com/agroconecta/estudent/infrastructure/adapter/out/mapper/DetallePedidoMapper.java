package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.DetallePedidoRequest;
import com.agroconecta.estudent.application.dto.response.DetallePedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.DetallePedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {

    @Mapping(target = "idDetallePedido", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    DetallePedidoEntity toEntity(DetallePedidoRequest request);

    @Mapping(target = "idDetallePedido", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget DetallePedidoEntity entity, DetallePedidoRequest request);

    @Mapping(source = "pedido.idPedido", target = "idPedido")
    @Mapping(source = "producto.idProducto", target = "idProducto")
    @Mapping(source = "producto.nombre", target = "nombreProducto")
    @Mapping(source = "lote.idLote", target = "idLote")
    @Mapping(source = "lote.codigoLote", target = "codigoLote")
    DetallePedidoResponse toResponse(DetallePedidoEntity entity);
}
