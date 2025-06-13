package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LeerApiKey {
    public static String obtenerApiKey() {
    	// Deserialización de la apiKey (guardada en el archivo 'apiKey.ser').
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("apiKey.ser"))) {
            return (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer la API Key: " + e.getMessage());
            return null;
        }
    }
}
