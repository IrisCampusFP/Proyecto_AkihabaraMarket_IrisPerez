package vista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InterfazConsolaPrincipal {
    private Scanner s = new Scanner(System.in);
    
    
    // Método para mostrar el menú principal
    public void menuPrincipal() {
        System.out.println("\nMENÚ PRINCIPAL:");
        System.out.println("1. Gestionar productos");
        System.out.println("2. Gestionar clientes");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }  
    
    
    public void menuCRUD(String tabla) {
    	System.out.println("\nMENÚ " + tabla.toUpperCase() + "S:");
        System.out.println("1. Añadir nuevo " + tabla);
        System.out.println("2. Consultar " + tabla + " por ID");
        System.out.println("3. Ver todos los " + tabla + "s registrados");
        System.out.println("4. Actualizar " + tabla);
        System.out.println("5. Eliminar " + tabla);
    }
    
    // Método para mostrar el menú de productos
    public void menuProductos() {
    	menuCRUD("producto");
        System.out.println("6. Buscar productos por nombre");
        System.out.println("7. Utilizar asistente IA");
        System.out.println("8. Volver");
        System.out.print("Selecciona una opción: ");
    }
    
    // Método para mostrar el menú de clientes
    public void menuClientes() {
        menuCRUD("cliente");
        System.out.println("6. Buscar clientes por email");
        System.out.println("7. Volver");
        System.out.print("Selecciona una opción: ");
    }
    
    // RECURSOS (métodos de control de errores y validación de entrada utilizados en el resto de interfaces)
    
    // Método que scannea la opción seleccionada por el usuario en un menú.
    public int leerOpcion() {
    	while (!s.hasNextInt()) {
            System.out.print("ERROR. Por favor, introduce un número correspondiente a una opción: ");
            s.next();
        }
        return s.nextInt();
    }
    
    // Método que controla y asegura que el usuario introduzca un número entero
    public int leerEntero() {
    	while (!s.hasNextInt()) {
    	    System.out.print("ERROR. Introduce un número entero: ");
    	    s.next();
    	}
    	return s.nextInt();
    }
    
    // Método que controla y asegura que el usuario introduzca un número entero o decimal
    public double leerDouble() {
    	while (!s.hasNextDouble()) {
            System.out.print("ERROR. Introduce un número decimal o entero: ");
            s.next();
        }
    	return s.nextDouble();
    }
    
    // Método que controla y asegura que el usuario introduzca una fecha válida
    public Date leerFecha() {
    	String fechaRecibida;
		while (true) {
		    fechaRecibida = s.nextLine();
		    try {
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		        formato.setLenient(false); // Esto comprueba que la fecha sea válida
		    	Date fecha = formato.parse(fechaRecibida); // Convierto la cadena recibida a formato fecha
		    	return fecha;
		    } catch (Exception e) {
		        System.out.print("Fecha inválida. Introduce una fecha válida (formato: yyyy-MM-dd): ");
		    }
		}
    }
    
    // Método que verifica que no se introduzca un email no válido
    public String validarEmail(String email) {
    	while ((email.isEmpty() || !email.contains("@") || !email.contains("."))) {
            System.out.print("ERROR. Introduce un email válido: ");
            email = s.nextLine();
        }
    	return email;
    }
    
}
