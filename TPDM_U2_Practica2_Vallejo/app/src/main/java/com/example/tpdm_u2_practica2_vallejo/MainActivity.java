package com.example.tpdm_u2_practica2_vallejo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Propietario vector[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.lista_propietarios);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                invocarDetalles(position);
            }
        });

    }

    private void invocarDetalles(int position) {
        Intent detalles = new Intent(this, DetallesPropietario.class );

        detalles.putExtra("TELEFONO", vector[position].getTelefono());
        detalles.putExtra("NOMBRE", vector[position].getNombre());
        detalles.putExtra("DOMICILIO", vector[position].getDomicilio());
        detalles.putExtra("FECHA", vector[position].getFecha());

        startActivity(detalles);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_insertar_Propietario:
                Intent nuevoPropietario = new Intent(this, InsertarPropietario.class);
                startActivity(nuevoPropietario);
                break;
            case R.id.item_consultar_Propietario:
                Intent consultarPropietario = new Intent(this, ConsultarPropietario.class);
                startActivity(consultarPropietario);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        Propietario propietario = new Propietario(this);
        vector = propietario.consultar();
        String [] telefonoNombre = null;

        if(vector == null){
            telefonoNombre = new String[1];
            telefonoNombre[0] = "No hay propietarios capturados";
        }else{
            telefonoNombre = new String[vector.length];
            for(int i = 0; i < vector.length; i++){
                Propietario temp = vector[i];
                telefonoNombre[i] = temp.getTelefono() + "\n" + temp.getNombre();

            }
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, telefonoNombre);

        lista.setAdapter(adaptador);

        super.onStart();
    }
}
