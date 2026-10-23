package com.mycompany.calculadoraadam;

import java.util.ArrayList;

public class ModeloCalculadora {

    private String numEnseñado = "0";       // texto mostrado
    private double acumulado = 0;       // resultado parcial
    private String operador = null;     // operación pendiente
    private boolean nuevoNum = true;    // próximo dígito empieza número
    private boolean error = false;      // división entre 0
    private ArrayList<OyenteModelo> oyentes = new ArrayList<>();

    public String getDisplay() {
        return numEnseñado;
    }

    public void addOyente(OyenteModelo o) {
        oyentes.add(o);
    }

    private void notificar() {
        for (OyenteModelo o : oyentes) {
            o.modeloCambiado();
        }
    }

    public void anadirDigito(String d) {
        if (error){
            limpiar();
        }                       // salir del error
        if (nuevoNum || numEnseñado.equals("0")) {
            numEnseñado = d;                            // reemplaza el 0 o empieza número
        } else {
            numEnseñado += d;
        }
        nuevoNum = false;
        notificar();
    }

    public void anadirPunto() {
        if (error) limpiar();
        if (nuevoNum) {
            numEnseñado = "0.";                         // ".5" -> "0.5"
            nuevoNum = false;
        } else if (!numEnseñado.contains(".")) {        // solo un punto
            numEnseñado += ".";
        }
        notificar();
    }

    public void operacion(String op) {
        if (error) return;
        if (operador != null && !nuevoNum) {
            aplicar(Double.parseDouble(numEnseñado));   // encadena operación
        } else if (operador == null) {
            acumulado = Double.parseDouble(numEnseñado);
        }
        if (!error) {
            numEnseñado = String.valueOf(acumulado);
            operador = op;                          // si pulsa dos operadores, cambia
            nuevoNum = true;
        }
        notificar();
    }

    public void resultado() {
        if (error || operador == null || nuevoNum) {
            return;
        }
        aplicar(Double.parseDouble(numEnseñado));
        if (!error) {
            numEnseñado = String.valueOf(acumulado);
            operador = null;
            nuevoNum = true;
        }
        notificar();
    }
    //Para la C que haga que todo vuelva a sus valores iniciales
    public void limpiar() {
        numEnseñado = "0";
        acumulado = 0;
        operador = null;
        nuevoNum = true;
        error = false;
        notificar();
    }

    // Calcula acumulado (operador) b
    private void aplicar(double b) {
        switch (operador) {
            case "+": acumulado += b; break;
            case "-": acumulado -= b; break;
            case "*": acumulado *= b; break;
            case "/":
                //No se puede dividir entre 0
                if (b == 0) {                      
                    numEnseñado = "Error";
                    error = true;
                    return;
                }
                acumulado /= b;
                break;
        }
    }

    
}
