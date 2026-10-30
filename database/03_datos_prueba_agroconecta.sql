-- =========================================================================
-- PROYECTO: AgroConecta Latam S.A.
-- MOTOR: PostgreSQL (Grupo 5)
-- ARCHIVO 3: Datos de Prueba (DML)
-- =========================================================================

-- 1. Centros de Acopio Regionales
INSERT INTO centros_acopio (nombre, pais, region) VALUES
('Acopio Central Lima', 'Perú', 'Lima'),
('Acopio Selva Central', 'Perú', 'Junín'),
('Acopio Eje Cafetero', 'Colombia', 'Antioquia'),
('Acopio Sierra Andina', 'Ecuador', 'Pichincha'),
('Acopio Altiplano', 'Bolivia', 'La Paz');

-- 2. Productores Agrícolas
INSERT INTO productores (nombre, pais, id_centro_acopio, tipo_producto_principal) VALUES
('Cooperativa Agraria Chanchamayo', 'Perú', 2, 'Café'),
('Asociación Productores del Cusco', 'Perú', 1, 'Quinua'),
('Hacienda Cafetera El Roble', 'Colombia', 3, 'Café'),
('Comunidad Cacaotera Manabí', 'Ecuador', 4, 'Cacao'),
('Productores Andinos de Oruro', 'Bolivia', 5, 'Quinua');

-- 3. Compradores
INSERT INTO compradores (razon_social, pais, email) VALUES
('Supermercados Wong S.A.', 'Perú', 'compras@wong.pe'),
('Café de Colombia Export S.A.S.', 'Colombia', 'adquisiciones@cafecol.com'),
('Chocolates Finos de Exportación', 'Ecuador', 'contacto@chocofino.ec'),
('Distribuidora Andina Bolivia S.R.L.', 'Bolivia', 'ventas@distandina.bo');

-- 4. Catálogo de Productos
INSERT INTO productos (nombre, categoria, unidad_medida, precio_referencial, id_productor) VALUES
('Café Especial Geisha 250g', 'Café', 'kg', 45.00, 1),
('Quinua Real Blanca Granel', 'Granos Andinos', 'kg', 12.50, 2),
('Café Orgánico de Altura', 'Café', 'kg', 38.00, 3),
('Cacao Fino de Aroma 100%', 'Cacao', 'kg', 28.00, 4),
('Quinua Roja Orgánica', 'Granos Andinos', 'kg', 15.00, 5);

-- 5. Inventario Inicial
INSERT INTO inventario (id_centro, id_producto, stock_actual, stock_minimo) VALUES
(2, 1, 500.00, 50.00),
(1, 2, 1200.00, 100.00),
(3, 3, 800.00, 80.00),
(4, 4, 35.00, 50.00), -- Genera alerta: stock_actual < stock_minimo
(5, 5, 450.00, 60.00);

-- 6. Pedido de Ejemplo (Activa triggers de cálculo y stock)
INSERT INTO pedidos (id_comprador, id_centro_origen, estado, monto_total) VALUES
(1, 1, 'Registrado', 250.00);

INSERT INTO detalle_pedidos (id_pedido, id_producto, cantidad, precio_unitario) VALUES
(1, 2, 20.00, 12.50);
