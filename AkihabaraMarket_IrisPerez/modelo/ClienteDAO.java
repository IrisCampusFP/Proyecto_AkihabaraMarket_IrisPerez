package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection conexion;

    public ClienteDAO() {
        this.conexion = ConexionBD.obtenerConexion();
    }

    // Agregar un solo cliente a la base de datos
    public boolean agregarCliente(ClienteOtaku cliente) {
        String query = "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";
        
        // Preparación de la consulta con los parámetros del cliente
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            // Ejecución de la consulta
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
            	return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar el cliente en la base de datos: " + e.getMessage());
        }
        return false;
    }

    // Obtener los datos de un cliente de la base de datos mediante su ID
    public ClienteOtaku obtenerClientePorId(int id) {
        String query = "SELECT * FROM clientes WHERE id = ?";
        
        // Preparación de la consulta
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            // Ejecución de la consulta
            ResultSet rs = stmt.executeQuery();
            // Si encuentra un cliente con ese id, devuelve un objeto con sus datos
            if (rs.next()) {
                return crearClienteRs(rs);
            } 
        } catch (SQLException e) {
            System.out.println("Error al obtener el cliente: " + e.getMessage());
        }
        return null;
    }

    // Obtener varios clientes de la base de datos
    public List<ClienteOtaku> obtenerClientes() {
        List<ClienteOtaku> listaClientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";

        // Preparación y ejecución de la consulta
        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

        	// Para cada resultado (cliente), se instancia un objeto con sus datos y se guarda en la lista
            while (rs.next()) {
                ClienteOtaku cliente = crearClienteRs(rs);
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los clientes: " + e.getMessage());
        }
        // Devuelve una lista con todos los clientes obtenidos de la base de datos
        return listaClientes;
    }

    // Actualizar un cliente en la base de datos
    public boolean actualizarCliente(ClienteOtaku cliente) {
        String query = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, fecha_registro = ? WHERE id = ?";
        // Preparación de la consulta con los datos del cliente actualizados
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setDate(4, new java.sql.Date(cliente.getFechaRegistro().getTime()));
            stmt.setInt(5, cliente.getId());
            // Ejecución de la consulta
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
            	return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente: " + e.getMessage());
        }
		return false;
    }

    // Eliminar un cliente de la base de datos
    public boolean eliminarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
            	return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
        return false;
    }

    // Obtener clientes de la base de datos cuyo/s nombre/s contenga/n el texto buscado
    public List<ClienteOtaku> buscarPorEmail(String email) {
        List<ClienteOtaku> clientesEncontrados = new ArrayList<>();
    	String query = "SELECT * FROM clientes WHERE email LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, "%" + email + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClienteOtaku cliente = crearClienteRs(rs);
                clientesEncontrados.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el cliente por email: " + e.getMessage());
        }
        return clientesEncontrados;
    }
    
    // Método que crea un objeto con los resultados obtenidos mediante ResultSet (con este método se evita la repetición de código)
    private ClienteOtaku crearClienteRs(ResultSet rs) throws SQLException {
        return new ClienteOtaku(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("email"),
            rs.getString("telefono"),
            rs.getDate("fecha_registro")
        );
    }

}
