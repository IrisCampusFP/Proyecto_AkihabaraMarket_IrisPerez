package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.ProductoOtaku;
import modelo.ProductoDAO;
import modelo.LlmService;

public class ProductosPanel {

    public static void mostrarPanelProductos() {
        ProductoDAO dao = new ProductoDAO();
        LlmService llm = new LlmService();
    	
    	// Configuración de la ventana de gestión de productos
        JFrame ventana = new JFrame("Gestión de Productos");
        ventana.setSize(800, 700); // Tamaño
        ventana.setLocationRelativeTo(null); // Centra la ventana en pantalla
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo se cierra esta ventana si se clica la x
        ventana.setLayout(new BorderLayout(10, 10)); // Márgenes

        // PANEL SUPERIOR: Título + Formulario + IA + Botones
        
        // Configuración del panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); 
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelSuperior.setBackground(new Color(245, 245, 245));

        // Título del panel superior
        JLabel tituloPanel = new JLabel("GESTIÓN DE PRODUCTOS", JLabel.CENTER);
        tituloPanel.setFont(new Font("Verdana", Font.BOLD, 20));
        tituloPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        tituloPanel.setForeground(new Color(52, 73, 94));
        panelSuperior.add(tituloPanel);
        panelSuperior.add(Box.createVerticalStrut(10)); 
        
        // FORMULARIO DE PRODUCTO
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setMaximumSize(new Dimension(500, 140)); 
        panelFormulario.setBackground(new Color(245, 245, 245));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Formulario Producto")); // Borde con título

        // Campos del formulario
        JTextField txtNombre = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();

        // Añadir etiquetas y campos al formulario
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        // Añado el formulario al panel superior
        panelSuperior.add(panelFormulario); 
        panelSuperior.add(Box.createVerticalStrut(10)); // Espacio vertical

        // PANEL PARA FUNCIONALIDADES DE INTELIGENCIA ARTIFICIAL
        JPanel panelIA = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelIA.setBackground(new Color(245, 245, 245));
        panelIA.setBorder(BorderFactory.createTitledBorder("Funciones Asistidas por IA"));

        // Botones del panel de IA
        JButton btnDescripcionIA = new JButton("Generar Descripción con IA");
        JButton btnCategoriaIA = new JButton("Sugerir Categoría con IA");

        panelIA.add(btnDescripcionIA);
        panelIA.add(btnCategoriaIA);

        panelSuperior.add(panelIA);
        panelSuperior.add(Box.createVerticalStrut(10));

        // PANEL CON BOTONES CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(245, 245, 245));
        
        // Creo y añado los botones
        JButton btnAgregar = new JButton("Agregar");
        JButton btnConsultar = new JButton("Consultar por ID");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscarNombre = new JButton("Buscar por nombre");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscarNombre);

        panelSuperior.add(panelBotones);
        ventana.add(panelSuperior, BorderLayout.PAGE_START);

        // TABLA DE PRODUCTOS
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Stock"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0); // Modelo vacío
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25); // Altura de las filas
        JScrollPane scrollTabla = new JScrollPane(tabla); // Scroll para la tabla
        ventana.add(scrollTabla, BorderLayout.CENTER);

        // BOTÓN VOLVER
        JButton btnVolver = new JButton("Volver al menú principal");
        btnVolver.setPreferredSize(new Dimension(250, 45));
        btnVolver.setMargin(new Insets(10, 20, 10, 20));
        btnVolver.setFont(btnVolver.getFont().deriveFont(Font.BOLD, 16f));
        

        ventana.add(btnVolver, BorderLayout.SOUTH);

        // CARGAR DATOS EN LA TABLA
        Runnable cargarProductos = () -> {
            modeloTabla.setRowCount(0); // Vacía la tabla
            for (ProductoOtaku p : dao.obtenerProductos()) {
                modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
            }
        };
        cargarProductos.run(); // Carga al iniciar

        
        // FUNCIONALIDADES CRUD:

        // AGREGAR PRODUCTO
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String categoria = txtCategoria.getText().trim();
                String precio = txtPrecio.getText().trim();
                String stock = txtStock.getText().trim();
                if (nombre.isEmpty() || categoria.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "Completa todos los campos.");
                    return;
                }
                double precioParseado = Double.parseDouble(precio);
                if (precioParseado < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
                int stockParseado = Integer.parseInt(stock);
                if (stockParseado < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
                ProductoOtaku nuevo = new ProductoOtaku(nombre, categoria, precioParseado, stockParseado);
                dao.agregarProducto(nuevo);
                JOptionPane.showMessageDialog(ventana, "Producto agregado.");
                txtNombre.setText(""); txtCategoria.setText(""); txtPrecio.setText(""); txtStock.setText("");
                cargarProductos.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage());
            }
        });

        // CONSULTAR PRODUCTO POR ID
        btnConsultar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del producto:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku producto = dao.obtenerProductoPorId(id);
                if (producto != null) {
                    JOptionPane.showMessageDialog(ventana,
                            "ID: " + producto.getId() + "\nNombre: " + producto.getNombre() +
                                    "\nCategoría: " + producto.getCategoria() + "\nPrecio: " +
                                    producto.getPrecio() + "\nStock: " + producto.getStock());
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // ACTUALIZAR PRODUCTO
        btnActualizar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del producto a modificar:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku producto = dao.obtenerProductoPorId(id);
                if (producto != null) {
                    String[] campos = {"Nombre", "Categoría", "Precio", "Stock"};
                    String campoSeleccionado = (String) JOptionPane.showInputDialog(
                            ventana,
                            "Selecciona el campo a modificar:",
                            "Modificar campo",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            campos,
                            campos[0]
                    );

                    if (campoSeleccionado != null) {
                        switch (campoSeleccionado) {
                            case "Nombre":
                                String nuevoNombre = JOptionPane.showInputDialog(ventana, "Nuevo nombre:", producto.getNombre());
                                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) producto.setNombre(nuevoNombre);
                                break;
                            case "Categoría":
                                String nuevaCategoria = JOptionPane.showInputDialog(ventana, "Nueva categoría:", producto.getCategoria());
                                if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) producto.setCategoria(nuevaCategoria);
                                break;
                            case "Precio":
                                String nuevoPrecio = JOptionPane.showInputDialog(ventana, "Nuevo precio:", producto.getPrecio());
                                double precio = Double.parseDouble(nuevoPrecio);
                                if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
                                producto.setPrecio(precio);
                                break;
                            case "Stock":
                                String nuevoStock = JOptionPane.showInputDialog(ventana, "Nuevo stock:", producto.getStock());
                                int stock = Integer.parseInt(nuevoStock);
                                if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
                                producto.setStock(stock);
                                break;
                        }
                        boolean actualizado = dao.actualizarProducto(producto);
                        JOptionPane.showMessageDialog(ventana, actualizado ? "Campo actualizado." : "Error al actualizar.");
                        cargarProductos.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage());
            }
        });

        // ELIMINAR PRODUCTO (POR ID)
        btnEliminar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del producto a eliminar:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku producto = dao.obtenerProductoPorId(id);
                if (producto != null) {
                    int confirm = JOptionPane.showConfirmDialog(ventana, "¿Estás seguro de que quieres eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION && dao.eliminarProducto(id)) {
                        JOptionPane.showMessageDialog(ventana, "Producto eliminado.");
                        cargarProductos.run();
                    } else {
                        JOptionPane.showMessageDialog(ventana, "No se ha eliminado el producto.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // BUSCAR PRODUCTOS POR NOMBRE
        btnBuscarNombre.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(ventana, "Introduce el nombre del producto a buscar:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                modeloTabla.setRowCount(0);
                for (ProductoOtaku p : dao.buscarProductosPorNombre(nombre.trim())) {
                    modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
                }
            }
        });
        
        
        // FUNCIONALIDADES IA:

        // GENERAR DESCRIPCIÓN CON IA
        btnDescripcionIA.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del producto para descripción IA:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku producto = dao.obtenerProductoPorId(id);
                if (producto != null) {
                    String prompt = "Genera una descripción de marketing breve y atractiva, en español, para el producto otaku: " + producto.getNombre() + " de la categoría " + producto.getCategoria() + ".";
                    String respuesta = llm.consultarLLM(prompt);
                    mostrarMensajeConScroll(ventana, "Descripción generada por IA", respuesta);
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // SUGERIR CATEGORÍA CON IA
        btnCategoriaIA.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(ventana, "Introduce el nombre del producto:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                String prompt = "Para un producto otaku llamado '" + nombre + "', sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
                String respuesta = llm.consultarLLM(prompt);
                mostrarMensajeConScroll(ventana, "Categoría sugerida por IA", respuesta);
            }
        });

        // Botón volver al menú principal
        btnVolver.addActionListener(e -> ventana.dispose());

        // Hace visible la ventana
        ventana.setVisible(true);
    }

    // Método que muestra una ventana con scroll para respuestas largas de la IA
    private static void mostrarMensajeConScroll(Component parent, String titulo, String mensaje) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setWrapStyleWord(true); // Wrap (palabras completas)
        textArea.setLineWrap(true); 
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(parent, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
