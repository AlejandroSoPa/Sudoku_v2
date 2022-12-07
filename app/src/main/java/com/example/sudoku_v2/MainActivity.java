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
    private Sudoku modelo=new Sudoku();
    private Spinner[][] matriz=new Spinner[9][9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout tabla = findViewById(R.id.tabla);
        CharSequence[] num = {" ","1","2","3","4","5","6","7","8","9"};
        modelo.creaPartida();


        for(int i = 0; i < 9; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < 9; j++) {
                Spinner spin = new Spinner(this);
                spin.setBackgroundResource(R.drawable.borde);
                spin.setTag(R.id.fila, i);
                spin.setTag(R.id.col, j);
                spin.setPadding(21, 25, 21, 25);
                spin.setTag("bug init");
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (adapterView.getTag().equals("bug init")) {
                            adapterView.setTag("bug resolved");
                        } else {
                            if (!(adapterView.getSelectedItem().toString().equals(" "))) {
                                int fila = (int) adapterView.getTag(R.id.fila);
                                int col = (int) adapterView.getTag(R.id.col);
                                int valor = Integer.parseInt(adapterView.getSelectedItem().toString());
                                if (modelo.setVal(fila, col, valor)) {
                                    Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                                    if (modelo.check()) {
                                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                        alertDialog.setTitle("Sudoku_v2");
                                        alertDialog.setMessage("Enhorabuena has completado el sudoku");
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
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, num);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);
                this.matriz[i][j]=spin;
                row.addView(spin);
            }
            tabla.addView(row);
        }
        refrescarGUI();
        refrescarGUI();
    }

    private void refrescarGUI(){
        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                if (matriz[i][j].getSelectedItem() != " "){
                    matriz[i][j].setSelection(modelo.getVal(i,j));
                    matriz[i][j].setEnabled(false);
                }else {
                    matriz[i][j].setSelection(modelo.getVal(i,j));
                    matriz[i][j].setEnabled(true);
                }
            }
        }
    }
}