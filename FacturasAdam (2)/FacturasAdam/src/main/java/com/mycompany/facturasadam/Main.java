package com.mycompany.facturasadam;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Ejecuta la interfaz gráfica en el hilo de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            GestorFacturas modelo = new GestorFacturas();
            VistaFacturas vista = new VistaFacturas();
            new Controlador(vista, modelo);
            vista.setVisible(true);
        });
    }
}
