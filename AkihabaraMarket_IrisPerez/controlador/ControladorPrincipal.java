package controlador;

import vista.InterfazConsolaPrincipal;

public class ControladorPrincipal {

    private InterfazConsolaPrincipal m = new InterfazConsolaPrincipal();
    private ControladorProductos pro = new ControladorProductos();
    private ControladorClientes cli = new ControladorClientes();
    
    /* Método que imprime el menú principal, recibe la opción seleccionada y 
     * redirige al usuario al método menú del controlador correspondiente. */
    public void menuPrincipal() {
        int opcion = 0;
        
        while (opcion != 3) {
        	m.menuPrincipal();
            opcion = m.leerOpcion();
            System.out.println();
            switch (opcion) {
                case 1:
                	pro.menuPrincipalProductos();
                	break;
                case 2:
                	cli.menuPrincipalClientes();
                	break;
                case 3:
                    System.out.println("Volviendo al menú de inicio...");
                	break;
                default:
                	System.out.println("Opción no válida. Por favor, introduce un número correspondiente a una opción.");
            }
        }
    }

}
