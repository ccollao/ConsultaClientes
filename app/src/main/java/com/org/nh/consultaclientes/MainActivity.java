package com.org.nh.consultaclientes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoc;
    int[] ecajas={R.id.inscribir,R.id.razon,R.id.rubro};
    ArrayAdapter<String> adaptador;
    ArrayList<String[]> clientes;
    ArrayList<String> nombres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoc = (AutoCompleteTextView) findViewById(R.id.autoc);
        leer_clientes();
        cargar_nombres();
        adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,nombres);
        autoc.setAdapter(adaptador);
        autoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                TextView tv = (TextView) view;
                String nrazon= tv.getText().toString();
                mostrar_cliente(nrazon);
            }
        });

    }

    private void mostrar_cliente(String nrazon) {
        int i=0;
        boolean encontre=false;
        while(!encontre){
            if(nombres.get(i).equals(nrazon)){
                String[] encontrado= clientes.get(i);
                for (int j = 0; j < encontrado.length; j++) {
                   TextView temporal = (TextView) findViewById(ecajas[j]);
                    temporal.setText(encontrado[j]);
                }
                encontre=true;
            }else
                i++;
        }
    }

    private void cargar_nombres() {
        nombres = new ArrayList<>();
        for (String[] item:clientes) {
          nombres.add(item[1]);
        }
    }

    private void leer_clientes() {
        String linea;
        clientes = new ArrayList<>();
        try {
            InputStream entrada= getAssets().open("clientes.txt");
            InputStreamReader lectura = new InputStreamReader(entrada);
            BufferedReader contenido = new BufferedReader(lectura);
            linea = contenido.readLine();
            while(linea != null && linea.length() >0){
                StringTokenizer corte = new StringTokenizer(linea,",");
                String[] dato = new String[3];
                for (int i = 0; i < dato.length; i++) {
                    dato[i] = corte.nextToken();
                }
              clientes.add(dato);
                linea = contenido.readLine();
            }
           contenido.close();
            lectura.close();
            entrada.close();
        } catch (IOException e) {
            Log.e("errorArchivo",e.getMessage());
        }
    }
}
