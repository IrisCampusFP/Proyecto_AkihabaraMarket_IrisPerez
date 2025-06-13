package controlador;

import vista.InterfazConsolaProductos;
import vista.InterfazConsolaPrincipal;
import modelo.LlmService;
import modelo.ProductoDAO;
import modelo.ProductoOtaku;

import java.util.List;

public class ControladorProductos {

    private InterfazConsolaPrincipal m = new InterfazConsolaPrincipal();
	private InterfazConsolaProductos vista = new InterfazConsolaProductos();
    private ProductoDAO dao = new ProductoDAO();
    private LlmService llm = new LlmService();

    /* Método principal que imprime el menú de productos, recibe la opción
     * seleccionada y redirige al usuario al método correspondiente. */
    public void menuPrincipalProductos() {
        int opcion = 0;
        
        while (opcion != 8) {
        	m.menuProductos();
            opcion = m.leerOpcion();

            System.out.println();
            switch (opcion) {
                case 1:
                	agregarProducto();
                	break;
                case 2:
                	consultarProductoPorId();
                	break;
                case 3:
                	verTodosLosProductos();
                	break;
                case 4:
                	actualizarProducto();
                	break;
                case 5:
                	eliminarProducto();
                	break;
                case 6:
                	buscarProductoPorNombre();
                	break;
                case 7:
                	menuAsistenteIA();
                	break;
                case 8:
                    System.out.println("Volviendo al menu de inicio...");
                    break;
                default:
                	System.out.println("Opción no válida. Por favor, introduce un número correspondiente a una opción.");
            }
        }
    }

    private void agregarProducto() {
    	// Se piden los datos del producto al usuario.
        ProductoOtaku producto = vista.pedirDatosProducto();
        // Se agrega un registro a la base de datos con esos datos.
        if (dao.agregarProducto(producto)) {
            System.out.println("Producto añadido correctamente.");
        } else {
        	System.out.println("ERROR: No se ha añadido el producto.");
        }
    }

    private void consultarProductoPorId() {
    	// Se pide el id al usuario.
        int id = vista.solicitarIdProducto();
        // Se envía el id al dao, que obtiene el producto con ese id y se guarda en una variable
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        if (producto != null) {
            // Se envía el producto obtenido a la vista para mostrar sus datos
            vista.mostrarDatosProducto(producto); 
        } else {
            System.out.println("No se ha encontrado ningún producto con el id: " + id);
        }
    }

    private void verTodosLosProductos() {
    	// Se obtiene una lista con todos los productos de la base de datos
        List<ProductoOtaku> listaProductos = dao.obtenerProductos();
        if (!listaProductos.isEmpty()) {
        	// Se envía la lista a la vista para mostrar los datos de todos los productos
            System.out.println("LISTA DE PRODUCTOS:");
            vista.mostrarListaProductos(listaProductos);
        } else {
        	System.out.println("No hay productos registrados.");
        }
    }

    private void actualizarProducto() {
    	// Se pide al usuario el id del producto a actualizar
        int id = vista.solicitarIdProducto();        
        // Se obtiene el producto a actualizar de la base de datos
        ProductoOtaku productoOriginal = dao.obtenerProductoPorId(id);
        // Si el producto existe, se piden al usuario los nuevos datos y se actualiza el registro en la base de datos
        if (productoOriginal != null) {
        	// Se muestran los datos del producto antes de actualizar
        	System.out.print("DATOS ACTUALES: ");
        	vista.mostrarDatosProducto(productoOriginal);
        	
            String nombre = productoOriginal.getNombre();
            String categoria = productoOriginal.getCategoria();
            double precio = productoOriginal.getPrecio();
            int stock = productoOriginal.getStock();
            
            int campo;
            do {
            	// Se pregunta el campo a modificar
            	vista.menuCampoAModificar();
            	campo = m.leerOpcion();
                
                switch (campo) {
                    case 1:
                        nombre = vista.actualizarNombre();
                        break;
                    case 2:
                        categoria = vista.actualizarCategoria();
                        break;
                    case 3:
                        precio = vista.actualizarPrecio();
                        break;
                    case 4:
                        stock = vista.actualizarStock();
                        break;
                    default:
        	            System.out.println("ERROR. Opción no válida. Introduce un número correspondiente a una opción.");
                }
            } while (campo < 1 || campo > 4); 
        	
            // Se pide el nuevo valor del campo
            ProductoOtaku productoActualizado = new ProductoOtaku(id, nombre, categoria, precio, stock);
            if (dao.actualizarProducto(productoActualizado)) {
                System.out.println("Producto actualizado correctamente.");
            } else {
            	System.out.println("ERROR: No se ha actualizado el producto.");
            }
        } else {
        	System.out.println("ERROR: No hay ningún producto registrado con el id: " + id);
        }
    }

    // Método que pide al usuario el id del producto a eliminar y lo elimina de la bdd
    private void eliminarProducto() {
    	// Se pide el usuario el id del producto a eliminar
        int id = vista.solicitarIdProducto();
        // Obtención de los datos del producto a eliminar
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        
        if (dao.obtenerProductoPorId(id) != null) {
        	// Se muestran los datos del producto
        	System.out.print("\nDATOS DEL PRODUCTO A ELIMINAR: ");
        	vista.mostrarDatosProducto(producto);
        	
        	// Pregunta de confirmación
        	int respuesta;
        	do {
        	    respuesta = vista.solicitarConfirmacion();
        	    switch (respuesta) {
        	        case 1:
        	            if (dao.eliminarProducto(id)) {
        	                System.out.println("Producto eliminado correctamente.");
        	            } else {
        	            	System.out.println("ERROR: No se ha eliminado el producto.");
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
        	System.out.println("ERROR: No se ha encontrado ningún producto con el id: " + id);
        }
    }
    
    private void buscarProductoPorNombre() {
    	// Se pide al usuario el nombre del producto
    	String nombreProducto = vista.pedirNombreProducto();
    	// Se obtiene una lista de productos con nombre coincidente de la base de datos
        List<ProductoOtaku> listaProductos = dao.buscarProductosPorNombre(nombreProducto);
        if (!listaProductos.isEmpty()) {
        	// Se envía la lista a la vista para mostrar los datos de todos los productos
            System.out.println("\nPRODUCTOS ENCONTRADOS:");
            vista.mostrarListaProductos(listaProductos);
        } else {
        	System.out.println("No se ha encontrado ningún producto.");
        }
    }
    
    // Método que muestra al usuario el menú del asistente IA y le redirige a la opción seleccionada
    private void menuAsistenteIA() {
    	int opcion = 0;
    	while (opcion != 3) {
    		vista.mostrarMenuIA();
            opcion = m.leerOpcion();
            
            System.out.println();
            switch (opcion) {
                case 1:
                	generarDescripcionProducto();
                	break;
                case 2:
                	sugerirCategoriaProducto();
                	break;
                case 3:
                	System.out.println("Volviendo al menú principal...");
                	break;
                default:
                	System.out.println("Opción no válida. Introduce un número correspondiente a una opción.");
            }
    	}
    }
    
    private void generarDescripcionProducto() {
    	// Se pide al usuario el ID de un producto
        int id = vista.solicitarIdProducto();
        // Se obtienen los datos del producto con ese ID (en la base de datos)
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        if (producto != null) {
        	// Se muestran los datos del producto
        	System.out.print("DATOS DEL PRODUCTO: ");
        	vista.mostrarDatosProducto(producto);
        	// Se construye el prompt con el nombre del producto y su categoría
        	String prompt = "Genera una descripción de marketing breve y atractiva, en español, para el producto otaku: " 
        		+ producto.getNombre() + " de la categoría " + producto.getCategoria() + ".";
        	// Se llama a la clase LlmService enviando el prompt para obtener la respuesta de la IA   
        	String respuesta = llm.consultarLLM(prompt);
        	// Se muestra al usuario la respuesta de la IA
        	vista.mostrarRespuestaIA(respuesta);
        } else {
            System.out.println("No se ha encontrado ningún producto con el ID: " + id);
        }
    }

    private void sugerirCategoriaProducto() {
    	// Se pide al usuario el nombre de un nuevo producto
        String nombre = vista.pedirNombreProducto();
        // Construcción del prompt con el nombre de producto recibido
        String prompt = "Para un producto otaku llamado '" + nombre + "', sugiere una categoría adecuada "
        		+ "de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
        String respuesta = llm.consultarLLM(prompt);
        // Se muestra al usuario la respuesta de la IA
        vista.mostrarRespuestaIA(respuesta);
    }

}
