package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.ClienteOtaku;
import modelo.ClienteDAO;

public class ClientesPanel {

    public static void mostrarPanelClientes() {
        ClienteDAO dao = new ClienteDAO();
    	
        // Configuración de la ventana de gestión de clientes
        JFrame ventana = new JFrame("Gestión de Clientes");
        ventana.setSize(800, 650); // Tamaño
        ventana.setLocationRelativeTo(null); // Centra la ventana
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana si se clica la x
        ventana.setLayout(new BorderLayout(10, 10)); // Márgenes

        // Título del panel superior
        JLabel tituloPanel = new JLabel("GESTIÓN DE CLIENTES", JLabel.CENTER);
        tituloPanel.setFont(new Font("Verdana", Font.BOLD, 20));
        tituloPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloPanel.setForeground(new Color(44, 62, 80));

        // PANEL SUPERIOR: Título + Formulario + Botones
        
        // Configuración del panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); // Diseño vertical
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Márgenes internos
        panelSuperior.setBackground(new Color(245, 245, 245));

        // FORMULARIO DE CLIENTE
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setMaximumSize(new Dimension(500, 100));
        panelFormulario.setBackground(new Color(245, 245, 245));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Formulario Cliente")); // Borde con título

        // Campos del formulario
        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();

        // Etiquetas y campos
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);

        // PANEL DE BOTONES CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(245, 245, 245));

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        JButton btnConsultar = new JButton("Consultar por ID");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscarEmail = new JButton("Buscar por Email");

        // Añadir botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscarEmail);

        // Añadir componentes al panel superior
        panelSuperior.add(tituloPanel);
        panelSuperior.add(Box.createVerticalStrut(10)); // Espacio
        panelSuperior.add(panelFormulario);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(panelBotones);

        // Añadir panel superior a la ventana
        ventana.add(panelSuperior, BorderLayout.PAGE_START);

        // TABLA DE CLIENTES
        String[] columnas = {"ID", "Nombre", "Email", "Teléfono", "Fecha de Registro"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        ventana.add(scrollTabla, BorderLayout.CENTER);

        // BOTÓN VOLVER
        JButton btnVolver = new JButton("Volver al menú principal");
        ventana.add(btnVolver, BorderLayout.SOUTH);
        btnVolver.setPreferredSize(new Dimension(250, 45));
        btnVolver.setMargin(new Insets(10, 20, 10, 20));
        btnVolver.setFont(btnVolver.getFont().deriveFont(Font.BOLD, 16f));

        // MÉTODO PARA CARGAR CLIENTES EN LA TABLA
        Runnable cargarClientes = () -> {
            modeloTabla.setRowCount(0); // Vacía la tabla
            for (ClienteOtaku c : dao.obtenerClientes()) {
                modeloTabla.addRow(new Object[]{
                    c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(), c.getFechaRegistro()
                });
            }
        };
        cargarClientes.run(); // Carga al iniciar

        // AGREGAR CLIENTE
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String email = txtEmail.getText().trim();
                String telefono = txtTelefono.getText().trim();

                if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "Completa todos los campos.");
                    return;
                }

                if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(ventana, "Email no válido.");
                    return;
                }

                ClienteOtaku nuevo = new ClienteOtaku(nombre, email, telefono);
                dao.agregarCliente(nuevo);
                JOptionPane.showMessageDialog(ventana, "Cliente agregado correctamente.");
                txtNombre.setText(""); txtEmail.setText(""); txtTelefono.setText("");
                cargarClientes.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage());
            }
        });

        // CONSULTAR CLIENTE POR ID
        btnConsultar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del cliente:");
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = dao.obtenerClientePorId(id);
                if (cliente != null) {
                    JOptionPane.showMessageDialog(ventana,
                        "ID: " + cliente.getId() + "\nNombre: " + cliente.getNombre() +
                        "\nEmail: " + cliente.getEmail() + "\nTeléfono: " + cliente.getTelefono() +
                        "\nFecha: " + cliente.getFechaRegistro());
                } else {
                    JOptionPane.showMessageDialog(ventana, "Cliente no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // ACTUALIZAR CLIENTE
        btnActualizar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del cliente a modificar:");
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = dao.obtenerClientePorId(id);
                if (cliente != null) {
                    String[] campos = {"Nombre", "Email", "Teléfono", "Fecha de Registro"};
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
                                String nuevoNombre = JOptionPane.showInputDialog(ventana, "Nuevo nombre:", cliente.getNombre());
                                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                                    cliente.setNombre(nuevoNombre.trim());
                                }
                                break;
                            case "Email":
                                String nuevoEmail = JOptionPane.showInputDialog(ventana, "Nuevo email:", cliente.getEmail());
                                if (nuevoEmail != null && !nuevoEmail.trim().isEmpty()) {
                                    if (nuevoEmail.isEmpty() || !nuevoEmail.contains("@") || !nuevoEmail.contains(".")) {
                                        JOptionPane.showMessageDialog(ventana, "Email no válido.");
                                        return;
                                    }
                                    cliente.setEmail(nuevoEmail.trim());
                                }
                                break;
                            case "Teléfono":
                                String nuevoTelefono = JOptionPane.showInputDialog(ventana, "Nuevo teléfono:", cliente.getTelefono());
                                if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
                                    cliente.setTelefono(nuevoTelefono.trim());
                                }
                                break;
                            case "Fecha de Registro":
                                String nuevaFecha = JOptionPane.showInputDialog(ventana, "Nueva fecha (Año-Mes-Día):", cliente.getFechaRegistro());
                                if (nuevaFecha != null && !nuevaFecha.trim().isEmpty()) {
                                    try {
                                        cliente.setFechaRegistro(java.sql.Date.valueOf(nuevaFecha.trim()));
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(ventana, "Formato de fecha inválido.");
                                        return;
                                    }
                                }
                                break;
                        }

                        boolean actualizado = dao.actualizarCliente(cliente);
                        JOptionPane.showMessageDialog(ventana, actualizado ? "Campo actualizado." : "Error al actualizar.");
                        cargarClientes.run();
                    }

                } else {
                    JOptionPane.showMessageDialog(ventana, "Cliente no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // ELIMINAR CLIENTE
        btnEliminar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "ID del cliente a eliminar:");
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = dao.obtenerClientePorId(id);
                if (cliente != null) {
                    int confirm = JOptionPane.showConfirmDialog(ventana,
                        "¿Estás segur@ de que quieres eliminar a este cliente?\n\n" +
                        "ID: " + cliente.getId() + "\nNombre: " + cliente.getNombre(),
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION && dao.eliminarCliente(id)) {
                        JOptionPane.showMessageDialog(ventana, "Cliente eliminado.");
                        cargarClientes.run();
                    } else {
                        JOptionPane.showMessageDialog(ventana, "No se ha eliminado el cliente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Cliente no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // BUSCAR CLIENTES POR EMAIL
        btnBuscarEmail.addActionListener(e -> {
            String email = JOptionPane.showInputDialog(ventana, "Introduce el email del cliente:");
            if (email != null && !email.trim().isEmpty()) {
                modeloTabla.setRowCount(0);
                for (ClienteOtaku cliente : dao.buscarPorEmail(email.trim())) {
                    modeloTabla.addRow(new Object[]{
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getTelefono(),
                        cliente.getFechaRegistro()
                    });
                }

                if (modeloTabla.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(ventana, "No se encontraron clientes con ese email.");
                }
            }
        });

        // BOTÓN VOLVER
        btnVolver.addActionListener(e -> ventana.dispose());

        // Hace visible la ventana
        ventana.setVisible(true);
    }
}
