package com.mycompany.tresenrayaadam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.addListener(this);
        // Turno inicial
        mostrarTurno();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = Integer.parseInt(e.getActionCommand());
        int jugador = modelo.getTurno();

        // Casilla ocupada
        if (!modelo.jugar(pos)) {
            return;
        }
        vista.pintar(pos, jugador);

        if (modelo.hayGanador()) {
            if (jugador == 1) {
                vista.mostrarMensaje("Ha ganado el jugador azul");
            } else {
                vista.mostrarMensaje("Ha ganado el jugador rojo");
            }
            reiniciar();
        } else if (modelo.tableroLleno()) {
            vista.mostrarMensaje("¡Empate!");
            reiniciar();
        } else {
            // Siguiente turno
            vista.mostrarMensaje(" ");
            modelo.cambiarTurno();
            mostrarTurno();
        }
    }

    private void reiniciar() {
        modelo.reiniciar();
        vista.reiniciar();
        // Vuelve a jugador 1
        mostrarTurno();
    }

    private void mostrarTurno() {
        if (modelo.getTurno() == 1) {
            vista.mostrarTurno("Juega el jugador 1.");
        } else {
            vista.mostrarTurno("Juega el jugador 2.");
        }
    }
}