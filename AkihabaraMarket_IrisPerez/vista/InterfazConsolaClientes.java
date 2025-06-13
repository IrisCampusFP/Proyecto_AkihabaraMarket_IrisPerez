package vista;

import java.util.List;		
import java.util.Scanner;
import modelo.ClienteOtaku;

import java.util.Date;

public class InterfazConsolaClientes {
    private Scanner s = new Scanner(System.in);
    private InterfazConsolaPrincipal interfazPrincipal = new InterfazConsolaPrincipal();
    
    // Método para solicitar datos de un nuevo cliente
    public ClienteOtaku pedirDatosCliente() {
        System.out.println("INTRODUCE LOS DATOS DEL CLIENTE");
        
        System.out.print("Nombre: ");
        String nombre;
        while ((nombre = s.nextLine().trim()).isEmpty()) { // Para evitar que el nombre este vacío
            System.out.print("ERROR. Debes introducir un nombre: ");
        }

        System.out.print("Email: ");
    	String email = s.nextLine();
    	email = interfazPrincipal.validarEmail(email);
        
        System.out.print("Telefono: ");
        String telefono = s.nextLine();

        return new ClienteOtaku(nombre, email, telefono);
    }

    // Método para solicitar un ID de cliente
    public int solicitarIdCliente() {
    	System.out.print("Introduce el ID del cliente: ");
    	return interfazPrincipal.leerEntero();
    }
    
    // Método para solicitar una confirmación antes de eliminar un usuario
    public int solicitarConfirmacion() {
    	System.out.println("\n¿Estás segur@ de que quieres eliminar este cliente?\n1- Sí, eliminar\n2- Cancelar");
        System.out.print("Respuesta: ");
        return interfazPrincipal.leerEntero();
    }
    

    // Método para mostrar un cliente
    public void mostrarDatosCliente(ClienteOtaku p) {
    	System.out.printf("ID: %d | Nombre: %s | Email: %s | Telefono: %s | Fecha de Registro: %s\n",
                p.getId(), p.getNombre(), p.getEmail(), p.getTelefono(), p.getFechaRegistro());
    }
    
    // Método para mostrar la lista de clientes
    public void mostrarListaClientes(List<ClienteOtaku> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("\nERROR. No hay ningún cliente registrado.");
        } else {
            for (ClienteOtaku p : clientes) {
                mostrarDatosCliente(p);
            }
        }
    }

    public void menuCampoAModificar() {
    	System.out.println("\n¿Qué campo quieres modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Email");
        System.out.println("3. Telefono");
        System.out.println("4. Fecha de registro");
        System.out.print("Selecciona una opción: ");
    }
    
    // Método para actualizar el nombre
    public String actualizarNombre() {
    	String nombre;
    	System.out.print("Nuevo nombre: ");
        while ((nombre = s.nextLine().trim()).isEmpty()) { // Para evitar que se introduzca un nombre vacío
            System.out.print("ERROR. Debes introducir un nombre: ");
        }
        return nombre;
    }
    
    public String actualizarEmail() {
    	System.out.print("Nuevo email: ");
    	String email = s.nextLine();
    	return interfazPrincipal.validarEmail(email);
    }
    
    public String actualizarTelefono() {
    	System.out.print("Nuevo telefono: ");
    	return s.nextLine();
    }
    
    public Date actualizarFechaRegistro() {
    	System.out.print("Nueva fecha de registro (Año-Mes-Día): ");
    	s.nextLine(); // Limpiar buffer
		return interfazPrincipal.leerFecha();
    }
    
    // Método que solicita el email del cliente
    public String pedirEmailCliente() {
        System.out.print("Introduce el email del cliente: ");
    	String email = s.nextLine();
        return interfazPrincipal.validarEmail(email);
    }

}
