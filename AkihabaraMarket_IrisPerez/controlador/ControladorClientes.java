package controlador;

import vista.InterfazConsolaClientes;	
import vista.InterfazConsolaPrincipal;
import modelo.ClienteDAO;
import modelo.ClienteOtaku;

import java.util.Date;
import java.util.List;

public class ControladorClientes {

    private InterfazConsolaPrincipal m = new InterfazConsolaPrincipal();
    private InterfazConsolaClientes vista = new InterfazConsolaClientes();
    private ClienteDAO dao = new ClienteDAO();


    /* Método principal que imprime el menú de clientes, recibe la opción
     * seleccionada y redirige al usuario al método correspondiente. */
    public void menuPrincipalClientes() {
        int opcion = 0;
        
        while (opcion != 7) {
        	m.menuClientes();
            opcion = m.leerOpcion();

            System.out.println();
            switch (opcion) {
                case 1:
                	agregarCliente();
                	break;
                case 2:
                	consultarClientePorId();
                	break;
                case 3:
                	verTodosLosClientes();
                	break;
                case 4:
                	actualizarCliente();
                	break;
                case 5:
                	eliminarCliente();
                	break;
                case 6:
                	buscarClientePorEmail();
                	break;
                case 7:
                    System.out.println("Volviendo al menú de inicio...");
                    break;
                default:
                	System.out.println("Opción no válida. Por favor, introduce un número correspondiente a una opción.");
            }
        }
    }

    private void agregarCliente() {
    	// Se piden los datos del cliente al usuario.
        ClienteOtaku cliente = vista.pedirDatosCliente();
        // Se comprueba si existe algún cliente registrado con ese correo
        if (dao.buscarPorEmail(cliente.getEmail()).isEmpty()) {
            // Se agrega un registro a la base de datos con esos datos.
            if (dao.agregarCliente(cliente)) {
                System.out.println("Cliente añadido correctamente.");
            } else {
            	System.out.println("ERROR: No se ha añadido el cliente.");
            }
        } else {
        	System.out.println("ERROR: Ya existe un cliente registrado con ese email.");
        }
    }

    private void consultarClientePorId() {
    	// Se pide el id al usuario.
        int id = vista.solicitarIdCliente();
        // Se envía el id al dao, que obtiene el cliente con ese id y se guarda en una variable
        ClienteOtaku cliente = dao.obtenerClientePorId(id);
        if (cliente != null) {
            // Se envía el cliente obtenido a la vista para mostrar sus datos
            vista.mostrarDatosCliente(cliente); 
        } else {
            System.out.println("No se ha encontrado ningún cliente con el id: " + id);
        }
    }

    private void verTodosLosClientes() {
    	// Se obtiene una lista con todos los clientes de la base de datos
        List<ClienteOtaku> listaClientes = dao.obtenerClientes();
        if (!listaClientes.isEmpty()) {
        	// Se envía la lista a la vista para mostrar los datos de todos los clientes
            System.out.println("LISTA DE CLIENTES:");
            vista.mostrarListaClientes(listaClientes);
        } else {
        	System.out.println("No hay clientes registrados.");
        }
    }

    private void actualizarCliente() {
    	// Se pide al usuario el id del cliente a actualizar
        int id = vista.solicitarIdCliente();        
        // Se obtiene el cliente a actualizar de la base de datos
        ClienteOtaku clienteOriginal = dao.obtenerClientePorId(id);
        // Si el cliente existe, se piden al usuario los nuevos datos y se actualiza el registro en la base de datos
        if (clienteOriginal != null) {
        	// Se muestran los datos del cliente antes de actualizar
        	System.out.print("DATOS ACTUALES: ");
        	vista.mostrarDatosCliente(clienteOriginal);
        	
            String nombre = clienteOriginal.getNombre();
            String email = clienteOriginal.getEmail();
            String telefono = clienteOriginal.getTelefono();
            Date fecha_registro = clienteOriginal.getFechaRegistro();
            
            int campo;
            do {
            	// Se pregunta el campo a modificar
            	vista.menuCampoAModificar();
            	campo = m.leerOpcion();
                
            	System.out.println();
                switch (campo) {
                    case 1:
                        nombre = vista.actualizarNombre();
                        break;
                    case 2:
                        email = vista.actualizarEmail();
                        break;
                    case 3:
                        telefono = vista.actualizarTelefono();
                        break;
                    case 4:
                        fecha_registro = vista.actualizarFechaRegistro();
                        break;
                    default:
        	            System.out.println("ERROR. Opción no válida. Introduce un número correspondiente a una opción.");
                }
            } while (campo < 1 || campo > 4); 
        	
            // Se pide el nuevo valor del campo
            ClienteOtaku clienteActualizado = new ClienteOtaku(id, nombre, email, telefono, fecha_registro);
            if (dao.actualizarCliente(clienteActualizado)) {
                System.out.println("Cliente actualizado correctamente.");
            } else {
            	System.out.println("ERROR: No se ha actualizado el cliente.");
            }
        } else {
        	System.out.println("ERROR: No hay ningún cliente registrado con el id: " + id);
        }
    }

    // Método que pide al usuario el id del cliente a eliminar y lo elimina de la bdd
    private void eliminarCliente() {
    	// Se pide el usuario el id del cliente a eliminar
        int id = vista.solicitarIdCliente();
        // Obtención de los datos del cliente a eliminar
        ClienteOtaku cliente = dao.obtenerClientePorId(id);
        
        if (dao.obtenerClientePorId(id) != null) {
        	// Se muestran los datos del cliente
        	System.out.print("\nDATOS DEL PRODUCTO A ELIMINAR: ");
        	vista.mostrarDatosCliente(cliente);
        	
        	// Pregunta de confirmación
        	int respuesta;
        	do {
        	    respuesta = vista.solicitarConfirmacion();
        	    switch (respuesta) {
        	        case 1:
        	            if (dao.eliminarCliente(id)) {
                            System.out.println("Cliente eliminado correctamente.");
        	            } else {
        	            	System.out.println("ERROR: No se ha eliminado el cliente.");
        	            }
        	            break;
        	        case 2:
        	            System.out.println("Operación cancelada, volviendo al menú.");
        	            break;
        	        default:
        	            System.out.println("ERROR. Opción no válida. Introduce un número correspondiente a una opción.\n");
        	    }
        	} while (respuesta != 1 && respuesta != 2);
        } else {
        	System.out.println("ERROR: No se ha encontrado ningún cliente con el id: " + id);
        }
    }
    
    private void buscarClientePorEmail() {
    	// Se pide al usuario el email del cliente
    	String emailCliente = vista.pedirEmailCliente();
    	// Se obtiene una lista de clientes con email coincidente de la base de datos
        List<ClienteOtaku> listaClientes = dao.buscarPorEmail(emailCliente);
        if (!listaClientes.isEmpty()) {
        	// Se envía la lista a la vista para mostrar los datos de todos los clientes
            System.out.println("\nCLIENTES ENCONTRADOS:");
            vista.mostrarListaClientes(listaClientes);
        } else {
        	System.out.println("No se ha encontrado ningún cliente.");
        }
    }

}
