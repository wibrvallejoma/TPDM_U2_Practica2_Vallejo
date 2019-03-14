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
import android.widget.Button;
import android.widget.ListView;

public class ConsultarSeguros extends AppCompatActivity {
    ListView lista;
    Seguro vector[];
    String telefonoPropietario;
    Button regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_seguros);

        lista = findViewById(R.id.lista_seguros);
        regresar = findViewById(R.id.consultarSeguros_btnRegresar);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarAlerta(position);
            }
        });
        Bundle parametros = getIntent().getExtras();
        telefonoPropietario = parametros.getString("TELEFONO_PROPIETARIO");

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mostrarAlerta(final int position) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle("Atencion");
        alerta.setMessage("Deseas modificar/editar el Seguro: " + vector[position].getDescripcion() + "?");
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                invocarEliminarActualizar(position);
            }
        });
        alerta.setNegativeButton("No", null);
        alerta.show();
    }

    private void invocarEliminarActualizar(int position) {
        Intent eliminaActualiza = new Intent(this, ActualizarEliminarSeguro.class );

        eliminaActualiza.putExtra("DESCRIPCION", vector[position].getDescripcion());
        eliminaActualiza.putExtra("FECHA", vector[position].getFecha());
        eliminaActualiza.putExtra("TIPO", vector[position].getTipo());
        eliminaActualiza.putExtra("TELEFONO_PROPIETARIO", vector[position].getTelefono_propietario());
        eliminaActualiza.putExtra("ID_SEGURO", vector[position].getIdSeguro());
        startActivity(eliminaActualiza);
    }


    @Override
    protected void onStart() {
        Seguro seguro = new Seguro(this);
        vector = seguro.consultar("TELEFONO_PROPIETARIO", telefonoPropietario);
        String [] telefonoDescripcion = null;

        if(vector == null){
            telefonoDescripcion = new String[1];
            telefonoDescripcion[0] = "No hay seguros capturados para "+telefonoPropietario;
        }else{
            telefonoDescripcion = new String[vector.length];
            for(int i = 0; i < vector.length; i++){
                Seguro temp = vector[i];
                telefonoDescripcion[i] = temp.getTelefono_propietario() + "\n" + temp.getDescripcion();

            }
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, telefonoDescripcion);

        lista.setAdapter(adaptador);

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_seguros, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_insertar_Seguro:
                Intent nuevoSeguro = new Intent(this, InsertarSeguro.class);
                nuevoSeguro.putExtra("TELEFONO_PROPIETARIO",telefonoPropietario);
                startActivity(nuevoSeguro);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
