package vista;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipalGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    // Botones principales del panel
    public JButton btnGestionarProductos;
    public JButton btnGestionarClientes;
    public JButton btnSalir;

    // Constructor
    public PanelPrincipalGUI() {
        // Configuración de la ventana del menú inicial (título, tamaño, etc.)
        setTitle("Sistema de gestión de inventario Akihabara Market - Iris Pérez Aparicio");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creación del panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Diseño en columna
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // Márgenes internos
        getContentPane().add(panel); // Agrego el panel al contenido de la ventana

        // Título principal
        JLabel titulo = new JLabel("AKIHABARA MARKET", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(new Color(33, 33, 33));
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(30));

        // Inicialización de los botones
        btnGestionarProductos = new JButton("Gestionar Productos");
        btnGestionarClientes = new JButton("Gestionar Clientes");
        btnSalir = new JButton("Salir");

        Font botonFont = new Font("Segoe UI", Font.PLAIN, 16); // Fuente para los botones

        // Agrupación de los botones en un array para aplicarles estilo con un bucle foreach
        JButton[] botones = {btnGestionarProductos, btnGestionarClientes, btnSalir};
        for (JButton btn : botones) {
            btn.setMaximumSize(new Dimension(300, 50)); // Tamaño máximo
            btn.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontal
            btn.setFont(botonFont);
            btn.setFocusPainted(false); 
            btn.setBackground(new Color(60, 120, 200)); 
            btn.setForeground(Color.WHITE); 
            panel.add(btn); 
            panel.add(Box.createVerticalStrut(20)); // Espacio entre botones
        }

        // Aplico un color rojo al botón de salir
        btnSalir.setBackground(new Color(200, 60, 60));

        // Llamada al método que vincula los botones con sus acciones correspondientes
        asignarEventos(this);

        // Hace visible la ventana
        setVisible(true);
    }

    // Método estático que vincula los botones con los paneles o acciones correspondientes
    public static void asignarEventos(PanelPrincipalGUI ventana) {
        ventana.btnGestionarProductos.addActionListener(e -> ProductosPanel.mostrarPanelProductos());
        ventana.btnGestionarClientes.addActionListener(e -> ClientesPanel.mostrarPanelClientes());
        ventana.btnSalir.addActionListener(e -> ventana.dispose());
    }
}
