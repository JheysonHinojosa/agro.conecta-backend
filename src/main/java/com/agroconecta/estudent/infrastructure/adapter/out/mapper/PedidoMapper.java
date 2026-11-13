package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.PedidoRequest;
import com.agroconecta.estudent.application.dto.response.PedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "comprador", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "estadoPedido", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "montoTotal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    PedidoEntity toEntity(PedidoRequest request);

    @Mapping(target = "idPedido", ignore = true)
    @Mapping(target = "codigoPedido", ignore = true)
    @Mapping(target = "comprador", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "estadoPedido", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "montoTotal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget PedidoEntity entity, PedidoRequest request);

    @Mapping(source = "comprador.idComprador", target = "idComprador")
    @Mapping(source = "comprador.nombreRazonSocial", target = "nombreComprador")
    @Mapping(source = "centroAcopio.idCentroAcopio", target = "idCentroAcopio")
    @Mapping(source = "centroAcopio.nombre", target = "nombreCentroAcopio")
    @Mapping(source = "estadoPedido.idEstadoPedido", target = "idEstadoPedido")
    @Mapping(source = "estadoPedido.nombre", target = "nombreEstadoPedido")
    @Mapping(source = "estadoPedido.esFinal", target = "estadoFinal")
    @Mapping(source = "moneda.idMoneda", target = "idMoneda")
    @Mapping(source = "moneda.codigo", target = "codigoMoneda")
    PedidoResponse toResponse(PedidoEntity entity);
}
