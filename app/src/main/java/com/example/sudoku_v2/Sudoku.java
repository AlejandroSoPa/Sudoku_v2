package com.example.sudoku_v2;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Sudoku extends AppCompatActivity {
    static int[][] matrizSudoku = new int[9][9];

    public static int getVal(int fila, int col) {
        return matrizSudoku[fila][col];
    }

    public static int setVal(int fila, int col, int valor) {
        int valorPrevio = getVal(fila, col);
        matrizSudoku[fila][col] = valor;
        if (valor != 0 && (!comprovaFila(fila) || !comprovaCol(col) || !comprovaQuad(fila, col))) {
            matrizSudoku[fila][col] = valorPrevio;
            return -1;
        }
        return valor;
    }

    public static boolean check(){
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if(getVal(fila, col) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] start(){
        for (int row = 0; row < 9; row ++) {
            for (int col = 0; col < 9; col++) {
                setVal(row, col, 0);
            }
        }
        return matrizSudoku;
    }

    private static boolean comprovaFila(int fila) {
        ArrayList<Integer> val = new ArrayList<Integer>();
        for (int col  = 0; col <9; col ++){
            if (matrizSudoku[fila][col] != 0) {
                if (val.contains(matrizSudoku[fila][col])) {
                    return false;
                }
                val.add(matrizSudoku[fila][col]);
            }
        }
        return true;
    }

    private static boolean comprovaCol(int col) {
        ArrayList<Integer> val = new ArrayList<Integer>();
        for (int fila = 0; fila < 9; fila++){
            if (matrizSudoku[fila][col] != 0){
                if (val.contains(matrizSudoku[fila][col])) {
                    return false;
                }
                val.add(matrizSudoku[fila][col]);
            }
        }
        return true;
    }

    private static boolean comprovaQuad (int fila, int col) {
        ArrayList<Integer> val = new ArrayList<Integer>();
        int inicioFila = 0;
        int inicioColumna = 0;
        if (fila < 3){
            inicioFila = 0;
            if (col<3) {
                inicioColumna = 0;
            }
            else if (col < 6){
                inicioColumna = 3;
            }
            else {
                inicioColumna = 6;
            }
        }else if (fila < 6) {
            inicioFila = 3;
            if (col<3) {
                inicioColumna = 0;
            }
            else if (col < 6){
                inicioColumna = 3;
            }
            else {
                inicioColumna = 6;
            }
        } else {
            inicioFila = 6;
            if (col<3) {
                inicioColumna = 0;
            }
            else if (col < 6){
                inicioColumna = 3;
            }
            else {
                inicioColumna = 6;
            }
        }
        for (int a = inicioFila; a < inicioFila+3 ; a++){
            for (int b = inicioColumna; b < inicioColumna + 3; b++) {
                for (int c = inicioFila; c < inicioFila + 3; c++) {
                    for (int d = inicioColumna; d < inicioColumna + 3; d++) {
                        if (a != c && b != d && matrizSudoku[a][b] != 0) {
                            if (matrizSudoku[a][b] == matrizSudoku[c][d]) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void creaPartida(){
        start();
        int fila=0;
        int col=0;
        for (int j = 0; j < 70; j++) {
            fila = (int) (Math.random() * 9);
            col = (int) (Math.random() * 9);
            matrizSudoku[fila][col] = 0;
        }
    }
}