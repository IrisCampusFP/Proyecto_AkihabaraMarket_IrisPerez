package controlador;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import modelo.SetupDatos;
import vista.*;


public class MainApp {

	public static void main(String[] args) {
		SetupDatos.insertarProductosEjemplo();
		SetupDatos.insertarClientesEjemplo();
		
		try (Scanner s = new Scanner(System.in)) {
			
			/* Se muestra al usuario un menú para elegir si quiere ejecutar
		     * el programa por consola o por interfaz gráfica. */
			
			int opcion = 0;
			while (opcion != 3 && opcion != 2) {
				System.out.println("\n---------------------------------");
				System.out.println("BIENVENID@ AL SISTEMA DE GESTIÓN");
				System.out.println("DE INVENTARIO DE AKIHABARA MARKET");
				System.out.println("---------------------------------");

				System.out.println("\nSELECCIONA UN MODO DE EJCUCIÓN:");
			    System.out.println("1. Interfaz de Consola");
			    System.out.println("2. Interfaz Gráfica");
			    System.out.println("3. Cerrar");
			    System.out.print("Elige una opción: ");	

			    try {
			        opcion = s.nextInt();
			    } catch (InputMismatchException e) { // Por si el usuario escribe texto
			        s.nextLine(); // Limpiar buffer 
			    }


				switch (opcion) {
			    	case 1:
			    		ControladorPrincipal controlador = new ControladorPrincipal();
			    		controlador.menuPrincipal();
			            break;
			        case 2:
			        	System.out.println("\nAbriendo interfaz gráfica (ejecución en consola finalizada).");
			        	SwingUtilities.invokeLater(() -> {
			                new PanelPrincipalGUI().setVisible(true);
			            });
			            break;
			        case 3:
			            System.out.println("\nCerrando la aplicación... ¡Hasta pronto!");
			            break;
			        default:
			            System.out.println("\nOpción no válida. Por favor, introduce un número correspondiente a una opción.");
				}
			}
		}	
	}
}
