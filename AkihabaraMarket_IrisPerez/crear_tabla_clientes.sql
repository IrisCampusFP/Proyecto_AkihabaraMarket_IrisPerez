DROP DATABASE IF EXISTS akihabara_db;
CREATE DATABASE akihabara_db;
USE akihabara_db;

-- Creación de la tabla productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    precio DECIMAL(10,2),
    stock INT
);

-- Creación de la tabla clientes
CREATE TABLE clientes (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
telefono VARCHAR(20),
fecha_registro DATE DEFAULT (CURRENT_DATE)
);
