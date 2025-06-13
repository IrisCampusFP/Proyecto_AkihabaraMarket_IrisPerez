package modelo;

import java.sql.Connection;				
import java.sql.DriverManager;
import java.sql.SQLException;

// Conexion a la base de datos (akihabara_db)

public class ConexionBD {
    public static Connection obtenerConexion() {
        String url = "jdbc:mysql://localhost:3306/akihabara_db";
        String usuario = "root";
        String contraseña = "1234";

        try {
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
