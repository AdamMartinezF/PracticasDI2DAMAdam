package com.mycompany.calculadoraadam;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControladorCalculadora {

    private final ModeloCalculadora modelo;
    private final VistaCalculadora vista;

    public ControladorCalculadora(ModeloCalculadora modelo, VistaCalculadora vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Modelo y Vista: actualiza el display
        modelo.addOyente(() -> vista.setDisplay(modelo.getDisplay()));
        vista.setDisplay(modelo.getDisplay());      // valor inicial

        // funcionalidades de los botones
        vista.addNumeroListener(e -> eventoNumero(e.getActionCommand()));
        vista.addPuntoListener(e -> eventoDecimal());
        vista.addOperacionListener(e -> eventosOperaciones(e.getActionCommand()));
        vista.addResultadoListener(e -> resultado());
        vista.addLimpiarListener(e -> limpiar());

        // Vista -> Controlador: teclado
        vista.addTecladoListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                eventoTeclado(e.getKeyChar());
            }
        });
    }

    // Dígito 0-9
    private void eventoNumero(String digito) {
        modelo.anadirDigito(digito);
    }

    // Punto decimal (un solo punto por número, ".5" -> "0.5")
    private void eventoDecimal() {
        modelo.anadirPunto();
    }

    // + - * / (calcula la pendiente y encadena)
    private void eventosOperaciones(String operador) {
        modelo.operacion(operador);
    }

    // Botón Resultado
    private void resultado() {
        modelo.resultado();
    }

    // Botón C
    private void limpiar() {
        modelo.limpiar();
    }

    // Traduce cada tecla a su evento
    private void eventoTeclado(char c) {
        if (c >= '0' && c <= '9') {
            eventoNumero(String.valueOf(c));
        } else if (c == '.' || c == ',') {
            eventoDecimal();
        } else if ("+-*/".indexOf(c) >= 0) {
            eventosOperaciones(String.valueOf(c));
        } else if (c == '=' || c == '\n') { //= o simplemente enter
            resultado();
        } else if (c == 'c' || c == 'C' || c == 27) { // C o Esc
            limpiar();
        }
    }
}
