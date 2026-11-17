package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record BootstrapResponse(
        List<PaisBootstrapDto> paises,
        List<CategoriaBootstrapDto> categorias,
        List<CentroBootstrapDto> centros,
        List<ProductorBootstrapDto> productores,
        List<CompradorBootstrapDto> compradores,
        List<ProductoBootstrapDto> productos,
        List<InventarioBootstrapDto> inventario,
        List<LoteBootstrapDto> lotes,
        List<PedidoBootstrapDto> pedidos,
        List<ResenaBootstrapDto> resenas,
        List<SensorBootstrapDto> sensores,
        List<UsuarioBootstrapDto> usuarios
) {
    public record PaisBootstrapDto(
            Integer id_pais,
            String nombre,
            String moneda
    ) {}

    public record CategoriaBootstrapDto(
            Integer id_categoria,
            String codigo
    ) {}

    public record CentroBootstrapDto(
            Long id_centro_acopio,
            String codigo,
            String nombre,
            Integer id_pais,
            String ciudad
    ) {}

    public record ProductorBootstrapDto(
            Long id_productor,
            Integer id_tipo_documento,
            String nro_documento,
            String nombre_razon_social,
            Integer id_pais,
            Long id_centro_acopio,
            Integer id_categoria_principal
    ) {}

    public record CompradorBootstrapDto(
            Long id_comprador,
            Integer id_tipo_comprador,
            Integer id_tipo_documento,
            String nro_documento,
            String nombre_razon_social,
            Integer id_pais
    ) {}

    public record ProductoBootstrapDto(
            Long id_producto,
            String codigo,
            String nombre,
            Integer id_categoria,
            Integer id_unidad_medida,
            BigDecimal precio_referencial,
            String moneda,
            Long id_productor
    ) {}

    public record InventarioBootstrapDto(
            Long id_inventario,
            Long id_centro_acopio,
            Long id_producto,
            BigDecimal stock_actual,
            BigDecimal stock_minimo
    ) {}

    public record LoteBootstrapDto(
            Long id_lote,
            String codigo_lote,
            Long id_producto,
            Long id_centro_acopio,
            String fecha_cosecha,
            BigDecimal cantidad_ingresada
    ) {}

    public record PedidoBootstrapDto(
            Long id_pedido,
            String codigo_pedido,
            Long id_comprador,
            Long id_centro_acopio,
            String estado,
            String moneda,
            BigDecimal monto_total,
            String fecha_registro,
            List<DetallePedidoBootstrapDto> detalle,
            List<HistorialPedidoBootstrapDto> historial
    ) {}

    public record DetallePedidoBootstrapDto(
            Long id_producto,
            Long id_lote,
            BigDecimal cantidad,
            BigDecimal precio_unitario
    ) {}

    public record HistorialPedidoBootstrapDto(
            String estado,
            String fecha
    ) {}

    public record ResenaBootstrapDto(
            Long id_resena,
            Long id_lote,
            Long id_comprador,
            Long id_pedido,
            Short puntuacion,
            String comentario
    ) {}

    public record SensorBootstrapDto(
            Long id_sensor,
            String codigo_sensor,
            Long id_centro_acopio,
            BigDecimal temp_min_c,
            BigDecimal temp_max_c,
            BigDecimal humedad_min_pct,
            BigDecimal humedad_max_pct,
            BigDecimal temperatura_c,
            BigDecimal humedad_pct,
            String fecha_lectura
    ) {}

    public record UsuarioBootstrapDto(
            Long id_usuario,
            String username,
            String email,
            String rol
    ) {}
}
