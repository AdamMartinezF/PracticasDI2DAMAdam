package com.mycompany.tresenrayaadam;

public class Modelo {

    // 0 vacío, 1 azul, 2 rojo
    private final int[] tablero = new int[9];
    private int turno = 1;
    private int jugadas = 0;

    // Combinaciones ganadoras
    private static final int[][] LINEAS = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    public boolean jugar(int pos) {
        // Casilla ocupada
        if (tablero[pos] != 0) {
            return false;
        }
        tablero[pos] = turno;
        jugadas++;
        return true;
    }

    public boolean hayGanador() {
        for (int[] l : LINEAS) {
            if (tablero[l[0]] != 0 && tablero[l[0]] == tablero[l[1]] && tablero[l[1]] == tablero[l[2]]) {
                return true;
            }
        }
        return false;
    }

    public boolean tableroLleno() {
        return jugadas == 9;
    }

    public void cambiarTurno() {
    if (turno == 1) {
        turno = 2;
    } else {
        turno = 1;
    }
}
    public int getTurno() {
        return turno;
    }

    public void reiniciar() {
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = 0;
        }
        turno = 1;
        jugadas = 0;
    }
}
