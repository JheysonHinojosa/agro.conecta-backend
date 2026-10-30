-- =========================================================================
-- PROYECTO: AgroConecta Latam S.A.
-- MOTOR: PostgreSQL (Grupo 5)
-- ARCHIVO 2: Triggers y Procedimientos Almacenados (Rúbrica 3.1)
-- =========================================================================

-- Tabla de auditoría
CREATE TABLE auditoria_precios (
    id_auditoria SERIAL PRIMARY KEY,
    id_producto INT NOT NULL,
    precio_anterior NUMERIC(10,2),
    precio_nuevo NUMERIC(10,2),
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TRIGGER 1: Auditoría de cambios de precio
CREATE OR REPLACE FUNCTION func_auditoria_precio() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.precio_referencial <> OLD.precio_referencial THEN
        INSERT INTO auditoria_precios(id_producto, precio_anterior, precio_nuevo)
        VALUES (OLD.id_producto, OLD.precio_referencial, NEW.precio_referencial);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auditoria_precio
AFTER UPDATE OF precio_referencial ON productos
FOR EACH ROW EXECUTE FUNCTION func_auditoria_precio();

-- TRIGGER 2: Descuento automático de inventario al registrar pedidos
CREATE OR REPLACE FUNCTION func_actualizar_stock() RETURNS TRIGGER AS $$
DECLARE
    v_centro_origen INT;
BEGIN
    SELECT id_centro_origen INTO v_centro_origen FROM pedidos WHERE id_pedido = NEW.id_pedido;
    
    UPDATE inventario
    SET stock_actual = stock_actual - NEW.cantidad
    WHERE id_producto = NEW.id_producto AND id_centro = v_centro_origen;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_stock
AFTER INSERT ON detalle_pedidos
FOR EACH ROW EXECUTE FUNCTION func_actualizar_stock();

-- PROCEDIMIENTO 1: Alerta de stock mínimo
CREATE OR REPLACE FUNCTION fn_alerta_stock_bajo()
RETURNS TABLE(centro VARCHAR, producto VARCHAR, stock_actual NUMERIC, stock_minimo NUMERIC) AS $$
BEGIN
    RETURN QUERY
    SELECT c.nombre::VARCHAR, p.nombre::VARCHAR, i.stock_actual, i.stock_minimo
    FROM inventario i
    JOIN centros_acopio c ON i.id_centro = c.id_centro
    JOIN productos p ON i.id_producto = p.id_producto
    WHERE i.stock_actual <= i.stock_minimo;
END;
$$ LANGUAGE plpgsql;

-- PROCEDIMIENTO 2: Calcular total de ventas por productor
CREATE OR REPLACE FUNCTION fn_calcular_ventas_productor(p_id_productor INT)
RETURNS NUMERIC AS $$
DECLARE
    v_total NUMERIC;
BEGIN
    SELECT COALESCE(SUM(dp.subtotal), 0) INTO v_total
    FROM detalle_pedidos dp
    JOIN productos p ON dp.id_producto = p.id_producto
    JOIN pedidos ped ON dp.id_pedido = ped.id_pedido
    WHERE p.id_productor = p_id_productor AND ped.estado != 'Cancelado';
    
    RETURN v_total;
END;
$$ LANGUAGE plpgsql;

-- PROCEDIMIENTO 3: Actualizar estado de pedido
CREATE OR REPLACE PROCEDURE sp_actualizar_estado_pedido(
    p_id_pedido INT, 
    p_nuevo_estado VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE pedidos 
    SET estado = p_nuevo_estado 
    WHERE id_pedido = p_id_pedido;
END;
$$;
