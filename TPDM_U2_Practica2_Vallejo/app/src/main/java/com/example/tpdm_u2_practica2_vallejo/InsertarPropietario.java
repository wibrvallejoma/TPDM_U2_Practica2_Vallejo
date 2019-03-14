package com.example.tpdm_u2_practica2_vallejo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertarPropietario extends AppCompatActivity {
    EditText telefono, nombre, domicilio, fecha;
    Button insertar, regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_propietario);

        telefono = findViewById(R.id.insertarPropietario_telefono);
        nombre = findViewById(R.id.insertarPropietario_nombre);
        domicilio = findViewById(R.id.insertarPropietario_domicilio);
        fecha = findViewById(R.id.insertarPropietario_fecha);

        insertar = findViewById(R.id.insertarPropietario_btnInsertar);
        regresar = findViewById(R.id.insertarPropietario_regresar);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarNuevoPropietario();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertarNuevoPropietario() {
        String mensaje = "";
        Propietario propietario = new Propietario(this);
        boolean respuesta = propietario.insertar(new Propietario(telefono.getText().toString(), nombre.getText().toString(),
                domicilio.getText().toString(), fecha.getText().toString()));

        if(respuesta){
            mensaje = "Se pudo insertar correctamente el Propietario: "+nombre.getText().toString();
        }else{
            mensaje = "Error, no se pudo crear el Propietario, respuesta de SQLite: "+propietario.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Atencion")
                .setMessage(mensaje)
                .setPositiveButton("ok", null)
                .show();
        telefono.setText("");
        nombre.setText("");
        domicilio.setText("");
        fecha.setText("");
    }
}
