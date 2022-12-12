package com.example.sudoku_v2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private Spinner[][] matriz =new Spinner[9][9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout tabla = findViewById(R.id.tabla);
        Sudoku.creaPartida();

        for(int i = 0; i < 9; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < 9; j++) {
                CharSequence[] num = {" ","1","2","3","4","5","6","7","8","9"};
                Spinner spin = new Spinner(this);
                spin.setBackgroundResource(R.drawable.borde);
                spin.setTag(R.id.fila, i);
                spin.setTag(R.id.col, j);
                spin.setPadding(21, 25, 21, 25);
                spin.setTag("init");
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (adapterView.getTag().equals("init")) {
                            adapterView.setTag("ta bien!");
                        } else {
                            if (!(adapterView.getSelectedItem().toString().equals(" "))) {
                                int fila = (int) adapterView.getTag(R.id.fila);
                                int col = (int) adapterView.getTag(R.id.col);
                                int valor = Integer.parseInt(adapterView.getSelectedItem().toString());

                                if (Sudoku.setVal(fila, col, valor) == 1) {
                                    Toast.makeText(getApplicationContext(), "Correcto, puedes continuar", Toast.LENGTH_LONG).show();
                                    if (Sudoku.check()) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                        alertDialog.setTitle("Sudoku_v2");
                                        alertDialog.setMessage("Enhorabuena, has completado el sudoku");
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Salir", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int l) {
                                                System.exit(0);
                                            }
                                        });
                                        alertDialog.show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrecto, prueba otra vez", Toast.LENGTH_LONG).show();
                                }
                                refrescarGUI();
                            } else {
                                adapterView.setSelection(0);
                                refrescarGUI();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, num);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                this.matriz[i][j]=spin;
                row.addView(spin);
            }
            tabla.addView(row);
        }
        deshabilitar();
    }

    private void refrescarGUI(){
        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                matriz[i][j].setSelection(Sudoku.getVal(i,j));
            }
        }
    }

    public void deshabilitar() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Sudoku.getVal(i,j) != 0) {
                    matriz[i][j].setSelection(Sudoku.getVal(i, j));
                    matriz[i][j].setEnabled(false);
                }
            }
        }
    }
}