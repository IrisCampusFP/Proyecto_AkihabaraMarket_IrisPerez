package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection conexion;

    public ProductoDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    // Agregar un solo producto a la base de datos
    public boolean agregarProducto(ProductoOtaku producto) {
        String query = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        
        // Preparación de la consulta con los parámetros del producto
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            // Ejecución de la consulta
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
            	return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar el producto en la base de datos: " + e.getMessage());
        }
        return false;
    }

    // Obtener los datos de un producto de la base de datos mediante su ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        String query = "SELECT * FROM productos WHERE id = ?";
        
        // Preparación de la consulta
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            // Ejecución de la consulta
            ResultSet rs = stmt.executeQuery();
            // Si encuentra un producto con ese id, devuelve un objeto con sus datos
            if (rs.next()) {
                return crearProductoRs(rs);
            } 
        } catch (SQLException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }
        return null;
    }

    // Obtener varios productos de la base de datos
    public List<ProductoOtaku> obtenerProductos() {
        List<ProductoOtaku> listaProductos = new ArrayList<>();
        String query = "SELECT * FROM productos";

        // Preparación y ejecución de la consulta
        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

        	// Para cada resultado (producto), se instancia un objeto con sus datos y se guarda en la lista
            while (rs.next()) {
                ProductoOtaku producto = crearProductoRs(rs);
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los productos: " + e.getMessage());
        }
        // Devuelve una lista con todos los productos obtenidos de la base de datos
        return listaProductos;
    }

    // Actualizar un producto en la base de datos
    public boolean actualizarProducto(ProductoOtaku producto) {
        String query = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        // Preparación de la consulta con los datos del producto actualizados
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());
            // Ejecución de la consulta
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
            	return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
        }
        return false;
    }

    // Eliminar un producto de la base de datos
    public boolean eliminarProducto(int id) {
        String query = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
            	return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
        return false;
    }

    // Obtener productos de la base de datos cuyo/s nombre/s contenga/n el texto buscado
    public List<ProductoOtaku> buscarProductosPorNombre(String nombreBuscado) {
        List<ProductoOtaku> listaProductos = new ArrayList<>();
        // Consulta flexible con LIKE para buscar por nombre
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        
        // Preparación de la consulta (uso de % para buscar nombres que contengan el texto buscado)
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, "%" + nombreBuscado + "%");
            // Ejecución de la consulta
            ResultSet rs = stmt.executeQuery();
            // Se guardan los resultados obtenidos en una lista
            while (rs.next()) {
                ProductoOtaku producto = crearProductoRs(rs);
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
        }
        // Devuelve la lista con los resultados obtenidos de la base de datos
        return listaProductos;
    }
    
    // Método que crea un objeto con los resultados obtenidos mediante ResultSet (con este método se evita la repetición de código)
    private ProductoOtaku crearProductoRs(ResultSet rs) throws SQLException {
        return new ProductoOtaku(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("categoria"),
            rs.getDouble("precio"),
            rs.getInt("stock")
        );
    }

}
