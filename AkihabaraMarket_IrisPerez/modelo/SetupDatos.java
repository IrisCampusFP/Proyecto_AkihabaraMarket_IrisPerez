package modelo;


import java.util.List;

public class SetupDatos {

    public static void insertarProductosEjemplo() {
        ProductoDAO productoDAO = new ProductoDAO();
        List<ProductoOtaku> listaProductos = productoDAO.obtenerProductos();

        // Inserción de productos de ejemplo en la base de datos solo si está vacía la tabla.
        if (listaProductos.isEmpty()) {
        	productoDAO.agregarProducto(new ProductoOtaku("Figura articulada de Gojo Satoru", "Figura", 64.90, 5));
        	productoDAO.agregarProducto(new ProductoOtaku("Manga Jujutsu Kaisen Vol.12", "Manga", 8.95, 18));
        	productoDAO.agregarProducto(new ProductoOtaku("Llavero de Nezuko Kamado (Kimetsu no Yaiba)", "Llavero", 4.50, 25));
        }
    }
    
    public static void insertarClientesEjemplo() {
        ClienteDAO ClienteDAO = new ClienteDAO();
        List<ClienteOtaku> listaClientes = ClienteDAO.obtenerClientes();

        // Inserción de clientes de ejemplo en la base de datos solo si está vacía la tabla.
        if (listaClientes.isEmpty()) {
        	ClienteDAO.agregarCliente(new ClienteOtaku("Laura Martínez", "laura.martinez@gmail.com", "654321678"));
        	ClienteDAO.agregarCliente(new ClienteOtaku("Carlos Pérez", "carlos.perez@gmail.com", "6667889443"));
        	ClienteDAO.agregarCliente(new ClienteOtaku("Marta García", "marta.garcia@gmail.com", "612543898"));
        }
    }
}
