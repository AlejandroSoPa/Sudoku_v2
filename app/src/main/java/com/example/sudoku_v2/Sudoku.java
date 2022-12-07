package com.example.sudoku_v2;

import androidx.appcompat.app.AppCompatActivity;

public class Sudoku extends AppCompatActivity {

    private static int[] val = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    private static int [][] sudoku= new int[9][9];
    private static int[][] respuesta = new int[9][9];

    public int getVal(int fila, int col) {
        return sudoku[fila][col];
    }

    public boolean setVal(int fila, int col, int valor) {
        int valorPrevio = sudoku[fila][col];
        sudoku[fila][col] = valor;
        if (sudoku[fila][col] != respuesta[fila][col]){
            sudoku[fila][col] = valorPrevio;
            return false;
        } else {
            sudoku[fila][col] = valor;
            return true;
        }
    }

    private static boolean correct(int fila, int col) {
        return (comprovaFila(fila) & comprovaCol(col) & comprovaQuad(fila, col));
    }

    public boolean check(){
        boolean valido = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!full(i,j)){
                    valido = false;
                    break;
                }
            }
        }
        return valido;
    }

    private static boolean full(int fila, int col){
        if (sudoku[fila][col] != respuesta[fila][col]){
            return false;
        } else {
            return true;
        }
    }

    public static int[][] start(){
        sudoku = new int[9][9];
        boolean flag;
        for (int i = 0; i < 9; i++) {
            int pos = 0;
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = generateValor(pos);
                flag=true;
                if (sudoku[i][j] == 0) {
                    if (j > 0) {
                        j = j - 2;
                        flag=false;
                    } else {
                        i--;
                        j = 8;
                        flag=false;
                    }
                }
                if (flag) {
                    if (correct(i, j)) {
                        pos = 0;
                    } else {
                        pos++;
                        j--;
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                respuesta[i][j] = sudoku[i][j];
            }
        }
        return sudoku;
    }

    private static boolean comprovaFila(int fila) {
        for (int k = 0; k<9; k++){
            for (int l = 0; l<9; l++){
                if (k != l && sudoku[fila][k] != 0){
                    if (sudoku[fila][k] == sudoku[fila][l]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean comprovaCol(int col) {
        for (int k = 0; k < 9; k++){
            for (int l = 0; l < 9; l++){
                if (k != l && sudoku[l][col] != 0){
                    if (sudoku[k][col] == sudoku[l][col]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean comprovaQuad (int fila, int col) {
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
                        if (a != c && b != d && sudoku[a][b] != 0) {
                            if (sudoku[a][b] == sudoku[c][d]) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void creaPartida(){
        start();
        int fila=0;
        int col=0;
        for (int j = 0; j < 70; j++) {
            fila = (int) (Math.random() * 9);
            col = (int) (Math.random() * 9);
            sudoku[fila][col] = 0;
        }
    }

    private static int generateValor(int pos) {
        if (pos == 0) {
            for (int i = 0; i < 9; i++) {
                val[i] = i + 1;
            }
        }
        if (pos == 9) {
            return 0;
        }
        int random=(int) (Math.random() * (9-pos));
        int tmp = val[8-pos];
        val[8-pos] = val[random];
        val[random] = tmp;
        return val[8-pos];
    }

}