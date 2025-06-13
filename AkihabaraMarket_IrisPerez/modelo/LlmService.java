package modelo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LlmService {

    private static final String ENDPOINT = "https://openrouter.ai/api/v1/chat/completions";
    private static final String MODELO = "mistralai/mistral-7b-instruct:free";
    private static final Gson gson = new Gson();
    

    public String consultarLLM(String prompt) {
    	// Obtención de la API Key
        String apiKey = LeerApiKey.obtenerApiKey();
        if (apiKey == null || apiKey.isEmpty()) {
            return "Error al obtener la API Key.";
        }
        
        try {
            // Creación del cuerpo JSON de la petición
            JsonObject cuerpo = new JsonObject();
            cuerpo.addProperty("model", MODELO);

            JsonArray mensajes = new JsonArray();
            JsonObject mensaje = new JsonObject();
            mensaje.addProperty("role", "user"); // El mensaje lo envía el usuario
            mensaje.addProperty("content", prompt); // Contenido del mensaje
            mensajes.add(mensaje);

            cuerpo.add("messages", mensajes);

            String json = gson.toJson(cuerpo);

            // Creación HttpClient (cliente)
            HttpClient cliente = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

            // Creación HttpRequest (petición)
            HttpRequest peticion = HttpRequest.newBuilder()
            		.uri(URI.create(ENDPOINT))
            		.timeout(Duration.ofSeconds(20))
            		.header("Content-Type", "application/json")
            		.header("Accept", "application/json")
                	.header("Authorization", "Bearer " + apiKey)
                	.POST(HttpRequest.BodyPublishers.ofString(json))
                	.build();

            // Se envía la petición recibiendo la respuesta en una variable
            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            int codigoRespuesta = respuesta.statusCode();
            String cuerpoRespuesta = respuesta.body();

            // Procesamiento de la respuesta según su código
            if (codigoRespuesta == 200 || codigoRespuesta == 201) {
                JsonObject jsonRespuesta = gson.fromJson(cuerpoRespuesta, JsonObject.class);
                JsonArray choices = jsonRespuesta.getAsJsonArray("choices");
                if (choices != null && choices.size() > 0) {
                    JsonObject mensajeRespuesta = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                    return mensajeRespuesta.get("content").getAsString().trim();
                } else {
                    return "La respuesta del modelo estaba vacía.";
                }
            } else {
                return manejarError(codigoRespuesta, cuerpoRespuesta);
            }

        } catch (Exception e) {
            return "Error inesperado al comunicarse con OpenRouter: " + e.getMessage();
        }
    }

    // Método que muestra un mensaje correspondiente al error según el código
    private String manejarError(int codigo, String respuesta) {
        String mensaje;
        switch (codigo) {
            case 400: 
            	mensaje = "Error 400: Petición incorrecta."; 
            	break;
            case 401: 
            	mensaje = "Error 401: No autorizado. (Verificar API Key)."; 
            	break;
            case 403: 
            	mensaje = "Error 403: Acceso prohibido."; 
            	break;
            case 404: 
            	mensaje = "Error 404: Recurso no encontrado."; 
            	break;
            case 500: 
            	mensaje = "Error 500: Error interno del servidor."; 
            	break;
            default: 
            	mensaje = "Error desconocido (" + codigo + ")";
            	break;
        }

        return mensaje + "\n" + respuesta;
    }
}
