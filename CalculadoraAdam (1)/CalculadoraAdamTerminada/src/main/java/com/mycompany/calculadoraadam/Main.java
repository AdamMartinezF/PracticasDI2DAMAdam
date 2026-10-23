package com.mycompany.calculadoraadam;

public class Main {

    public static void main(String[] args) {
        ModeloCalculadora modelo = new ModeloCalculadora();
        VistaCalculadora vista = new VistaCalculadora();
        new ControladorCalculadora(modelo, vista);
        // conecta las tres partes y las visibiliza
        vista.setVisible(true);
    }
}
