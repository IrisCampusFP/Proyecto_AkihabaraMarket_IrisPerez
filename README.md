# Sistema de gestión de inventario - Akihabara Market

**Desarrollado por:** Iris Pérez Aparicio  
**Curso:** 1º DAM - Campus FP Humanes  
**Prácticas:** SEIDOR  

---

## Descripción General

Akihabara Market es una aplicación desarrollada en Java que permite gestionar el inventario de una tienda otaku. Cuenta con una interfaz de consola y soporte para una interfaz gráfica en Swing. Utiliza MySQL como base de datos y se integra con un LLM mediante la API de OpenRouter para generar descripciones automáticas y sugerencias de categorías.

---

## Configuración del Entorno ⚙️

### Configuración de la Base de Datos MySQL 🐬

1. Inicia tu servidor MySQL y accede a la consola de administración.
2. Crea la base de datos con el nombre `akihabara_db`.
3. Ejecuta el siguiente script SQL, incluido en el archivo `crear_tabla.sql`, para crear las tablas `productos` y `clientes`:

```sql
DROP DATABASE IF EXISTS akihabara_db;
CREATE DATABASE IF NOT EXISTS akihabara_db;
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
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT (CURRENT_DATE)
);
```

### Configuración de la API Key de OpenRouter 🔑

1. Regístrate en [openrouter.ai](https://openrouter.ai) y genera una nueva API Key desde [aquí](https://openrouter.ai/settings/keys).
2. Abre el archivo `GuardarApiKey.java` en el paquete `modelo`.
3. Sustituye el contenido de `String clave = "";` por tu API Key entre comillas.
4. Ejecuta el archivo para que se genere el archivo `apiKey.ser` en el directorio del proyecto.
5. ¡Listo! La aplicación leerá la clave automáticamente desde ese archivo al ejecutar.

---

## Compilación y Ejecución

### Requisitos

- Java JDK 21+
- MySQL Server
- Conector JDBC para MySQL (incluido en el proyecto o agregar manualmente)
- Eclipse o cualquier IDE de tu elección

### Pasos

1. Abre el proyecto en tu IDE.
2. Asegúrate de tener configurado correctamente el `classpath` para el conector JDBC y la librería Gson.
3. Verifica que el archivo `apiKey.ser` exista en la raíz del proyecto.
4. Ejecuta la clase `MainApp.java` para lanzar la aplicación por consola.

---

## Funcionalidades

### Gestión de Productos
- Añadir, consultar, actualizar, eliminar y listar productos.
- Buscar productos por nombre.
- Validación de entradas de usuario.
- Se insertan productos de ejemplo si la base de datos está vacía.

### Funciones con Inteligencia Artificial (OpenRouter)
- **Generar descripción** de producto usando IA.
- **Sugerir categoría** para producto a partir del nombre.
- Integración mediante `LlmService` usando el modelo `"mistralai/mistral-7b-instruct:free"`.

### Gestión de Clientes 👥 (BONUS QUEST II)
- Alta, baja, modificación y listado de clientes.
- Búsqueda por email.
- Validación de datos.

### Interfaz Gráfica 🎨 (BONUS QUEST I)
- Paneles para gestión visual de productos y clientes con Java Swing.
- Funciones reflejadas desde la consola con validaciones gráficas.

---


## Estructura del Proyecto 🧱

- **`controlador/`**  
  Contiene los controladores que gestionan la lógica de negocio desde el menú principal y los submenús.  
  - `ControladorClientes.java`: Lógica de la gestión de clientes.  
  - `ControladorPrincipal.java`: Muestra el menú principal y redirige a los submenús.  
  - `ControladorProductos.java`: Lógica de la gestión de productos.  
  - `MainApp.java`: Clase principal que inicia la aplicación.

- **`modelo/`**  
  Incluye las clases del modelo de datos, conexión a la base de datos y servicios relacionados.  
  - `ClienteDAO.java`, `ProductoDAO.java`: Clases DAO para operaciones CRUD sobre clientes y productos.  
  - `ClienteOtaku.java`, `ProductoOtaku.java`: Clases que representan los objetos cliente y producto.  
  - `ConexionDB.java`: Establece la conexión con la base de datos MySQL.  
  - `GuardarApiKey.java`, `LeerApiKey.java`: Serialización y deserialización de la API Key para la IA.  
  - `LlmService.java`: Comunicación con la API de OpenRouter para generar descripciones y categorías.  
  - `SetupDatos.java`: Clase para inicializar datos si es necesario.

- **`vista/`**  
  Aquí van las interfaces gráficas (Java Swing) para la interacción visual con el usuario.  
  - `ClientesPanel.java`: Panel Swing para gestionar clientes.  
  - `ProductosPanel.java`: Panel Swing para gestionar productos.  
  - `InterfazConsolaClientes.java`: Interfaz de usuario en consola para gestionar clientes.  
  - `InterfazConsolaPrincipal.java`: Interfaz de usuario en consola para gestionar el menú principal.  
  - `InterfazConsolaProductos.java`: Interfaz de usuario en consola para gestionar productos.  
  - `PanelPrincipalGUI.java`: Interfaz gráfica principal con Java Swing.  

- **`jar/`**  
  Librerías externas necesarias para la ejecución del proyecto.  
  - `gson-2.10.1.jar`: Usada para trabajar con JSON.  
  - `mysql-connector-j-9.3.0.jar`: Conector JDBC para MySQL.

- **`apiKey.ser`**  
  Archivo serializado que guarda la clave API de OpenRouter.


---

## Notas adicionales 🔧

- **Tecnologías Utilizadas:**
  - Lenguaje: Java 21.0.5
  - Base de datos: MySQL
  - API Externa: OpenRouter API (para la integración con LLM)
  - Librerías: Conector JDBC para MySQL, GSON para manejar JSON en las interacciones con la IA.

- **Pruebas exhaustivas:**
  - El proyecto incluye una serie de pruebas para garantizar que todas las funcionalidades CRUD y la interacción con la IA funcionen correctamente, así como para manejar posibles errores como entradas no válidas o productos inexistentes.

- **Interfaz gráfica con Swing:**
  - En el **Bonus Quest** se ha implementado una interfaz gráfica utilizando Swing, con paneles para gestionar productos y clientes visualmente.
  - Los formularios de productos incluyen validaciones, como la comprobación de campos vacíos y formatos incorrectos.

---

Gracias por leer. Sistema de Gestión de Inventario Akihabara Market. Iris Pérez Aparicio :).
