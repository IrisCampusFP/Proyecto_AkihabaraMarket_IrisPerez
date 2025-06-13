package vista;

import java.util.List;
import java.util.Scanner;
import modelo.ProductoOtaku;

public class InterfazConsolaProductos {
    private Scanner s = new Scanner(System.in);
    private InterfazConsolaPrincipal interfazPrincipal = new InterfazConsolaPrincipal();
    
    // Método para solicitar datos de un nuevo producto
    public ProductoOtaku pedirDatosProducto() {
        System.out.println("INTRODUCE LOS DATOS DEL PRODUCTO");
        
        System.out.print("Nombre: ");
        String nombre;
        while ((nombre = s.nextLine().trim()).isEmpty()) { // Para evitar que se introduzca un nombre vacío
            System.out.print("ERROR. Debes introducir un nombre: ");
        }

        System.out.print("Categoría: ");
        String categoria = s.nextLine();

        System.out.print("Precio: ");
        double precio;
        do {
        	precio = interfazPrincipal.leerDouble();
            if (precio < 0) {
            	System.out.print("ERROR. El precio no puede ser negativo: ");
            }
        } while (precio < 0);
        
        System.out.print("Stock: ");
        int stock = interfazPrincipal.leerEntero();

        return new ProductoOtaku(nombre, categoria, precio, stock);
    }

    // Método para solicitar un ID de producto
    public int solicitarIdProducto() {
    	System.out.print("Introduce el ID del producto: ");
    	return interfazPrincipal.leerEntero();
    }
    
    // Método para solicitar una confirmación antes de eliminar un usuario
    public int solicitarConfirmacion() {
    	System.out.println("\n¿Estás segur@ de que quieres eliminar este producto?\n1- Sí, eliminar\n2- Cancelar");
        System.out.print("Respuesta: ");
        return interfazPrincipal.leerEntero();
    }
    

    // Método para mostrar un producto
    public void mostrarDatosProducto(ProductoOtaku p) {
    	System.out.printf("ID: %d | Nombre: %s | Categoría: %s | Precio: %.2f€ | Stock: %d\n",
                p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock());
    }
    
    // Método para mostrar la lista de productos
    public void mostrarListaProductos(List<ProductoOtaku> productos) {
        if (productos.isEmpty()) {
            System.out.println("\nERROR. No hay ningún producto registrado.");
        } else {
            for (ProductoOtaku p : productos) {
                mostrarDatosProducto(p);
            }
        }
    }

    public void menuCampoAModificar() {
    	System.out.println("\n¿Qué campo quieres modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Categoría");
        System.out.println("3. Precio");
        System.out.println("4. Stock");
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
    
    public String actualizarCategoria() {
    	System.out.print("Nueva categoría: ");
        return s.nextLine();
    }
    
    public double actualizarPrecio() {
    	System.out.print("Nuevo precio: ");
        double precio;
    	do {
    		precio = interfazPrincipal.leerDouble();
            if (precio < 0) {
            	System.out.print("ERROR. El precio no puede ser negativo: ");
            }
        } while (precio < 0);
    	return precio;
    }
    
    public int actualizarStock() {
    	System.out.print("Nuevo stock: ");
    	return interfazPrincipal.leerEntero();
    }
    
    
    // IA:
    
    public void mostrarMenuIA() {
    	System.out.println("\nFUNCIONES ASISTIDAS POR IA:");
    	System.out.println("1. Generar descripción de producto con IA.");
        System.out.println("2. Sugerir categoría para producto con IA.");
        System.out.println("3. Volver al menú de productos.");
        System.out.print("Selecciona una opción: ");
    }
    
    
    // Método que solicita al usuario el nombre de un producto
    public String pedirNombreProducto() {
        System.out.print("Introduce el nombre del producto: ");
        return s.nextLine();
    }

    // Método que muestra el resultado del LLM
    public void mostrarRespuestaIA(String textoGenerado) {
        System.out.println("\nRESPUESTA DE LA IA:");
        System.out.println(textoGenerado);
    }

}
