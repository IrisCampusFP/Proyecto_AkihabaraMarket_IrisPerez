package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GuardarApiKey {

    public static void main(String[] args) {
        // INTRODUCIR AQUÍ LA CLAVE (API Key) para guardarla en el proyecto de forma segura
        String clave = "";

        // Serialización de la clave (se guarda en el archivo 'apiKey.ser').
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("apiKey.ser"))) {
            out.writeObject(clave);
            System.out.println("API Key guardada correctamente en el archivo 'apiKey.ser'.");
        } catch (IOException e) {
            System.out.println("Error al guardar la API Key: " + e.getMessage());
        }
    }
}
