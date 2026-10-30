-- =========================================================================
-- PROYECTO: AgroConecta Latam S.A.
-- MOTOR: PostgreSQL (Grupo 5)
-- ARCHIVO 1: Esquema de Tablas (3FN) y Roles de Seguridad
-- =========================================================================

-- 1. Centros de Acopio
CREATE TABLE centros_acopio (
    id_centro SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    pais VARCHAR(50) NOT NULL CHECK (pais IN ('Perú', 'Colombia', 'Ecuador', 'Bolivia')),
    region VARCHAR(50) NOT NULL
);

-- 2. Productores
CREATE TABLE productores (
    id_productor SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    id_centro_acopio INT NOT NULL REFERENCES centros_acopio(id_centro),
    tipo_producto_principal VARCHAR(50)
);

-- 3. Compradores
CREATE TABLE compradores (
    id_comprador SERIAL PRIMARY KEY,
    razon_social VARCHAR(150) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- 4. Catálogo de Productos
CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    precio_referencial NUMERIC(10,2) NOT NULL CHECK (precio_referencial > 0),
    id_productor INT NOT NULL REFERENCES productores(id_productor)
);

-- 5. Inventario por Centro de Acopio
CREATE TABLE inventario (
    id_centro INT NOT NULL REFERENCES centros_acopio(id_centro),
    id_producto INT NOT NULL REFERENCES productos(id_producto),
    stock_actual NUMERIC(10,2) NOT NULL DEFAULT 0,
    stock_minimo NUMERIC(10,2) NOT NULL DEFAULT 100,
    PRIMARY KEY (id_centro, id_producto)
);

-- 6. Pedidos
CREATE TABLE pedidos (
    id_pedido SERIAL PRIMARY KEY,
    id_comprador INT NOT NULL REFERENCES compradores(id_comprador),
    id_centro_origen INT NOT NULL REFERENCES centros_acopio(id_centro),
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(30) DEFAULT 'Registrado' CHECK (estado IN ('Registrado', 'En tránsito', 'Entregado', 'Cancelado')),
    monto_total NUMERIC(12,2) DEFAULT 0
);

-- 7. Detalle de Pedidos
CREATE TABLE detalle_pedidos (
    id_pedido INT NOT NULL REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    id_producto INT NOT NULL REFERENCES productos(id_producto),
    cantidad NUMERIC(10,2) NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10,2) NOT NULL CHECK (precio_unitario > 0),
    subtotal NUMERIC(12,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    PRIMARY KEY (id_pedido, id_producto)
);

-- =========================================================================
-- ROLES Y PERMISOS (Rúbrica 3.1)
-- =========================================================================
CREATE ROLE administrador WITH LOGIN PASSWORD 'Admin_Agro2026';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO administrador;

CREATE ROLE operador_almacen WITH LOGIN PASSWORD 'Operador_Agro2026';
GRANT SELECT, INSERT, UPDATE ON inventario, pedidos, detalle_pedidos TO operador_almacen;

CREATE ROLE solo_lectura WITH LOGIN PASSWORD 'Reportes_Agro2026';
GRANT SELECT ON ALL TABLES IN SCHEMA public TO solo_lectura;
