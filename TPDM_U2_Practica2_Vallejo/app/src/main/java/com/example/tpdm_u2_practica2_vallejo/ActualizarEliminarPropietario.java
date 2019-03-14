package com.example.tpdm_u2_practica2_vallejo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActualizarEliminarPropietario extends AppCompatActivity {
    EditText nombre, domicilio, fecha;
    TextView titulo;
    Button actualizar, eliminar, regresar;
    String idPropietario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_eliminar_propietario);

        nombre = findViewById(R.id.actualizarEliminarPropietario_nombre);
        domicilio = findViewById(R.id.actualizarEliminarPropietario_domicilio);
        fecha = findViewById(R.id.actualizarEliminarPropietario_fecha);
        titulo = findViewById(R.id.actualizarEliminarPropietario_titulo);

        actualizar = findViewById(R.id.actualizarEliminarPropietario_btnActualizar);
        eliminar = findViewById(R.id.actualizarEliminarPropietario_btnEliminar);
        regresar = findViewById(R.id.actualizarEliminarPropietario_btnRegresar);
        Bundle parametros = getIntent().getExtras();
        titulo.setText("Editando "+parametros.getString("TELEFONO"));
        nombre.setText(parametros.getString("NOMBRE"));
        domicilio.setText(parametros.getString("DOMICILIO"));
        fecha.setText(""+parametros.getString("FECHA"));
        idPropietario = parametros.getString("TELEFONO");

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void eliminar() {
        Propietario a = new Propietario(this);
        String mensaje;
        boolean respuesta = a.eliminar(new Propietario(idPropietario, nombre.getText().toString(),
                domicilio.getText().toString(), fecha.getText().toString()));
        if(respuesta){
            mensaje = "Se pudo eliminar correctamente el Propietario: "+nombre.getText().toString();
            finish();
        }else{
            mensaje = "Error algo salio mal: " + a.error;
        }
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void actualizar() {
        Propietario a = new Propietario(this);
        String mensaje;
        boolean respuesta = a.actualizar(new Propietario(idPropietario, nombre.getText().toString(),
                domicilio.getText().toString(), fecha.getText().toString()));
        if(respuesta){
            mensaje = "Se actualizo correctamente el Propietario: "+nombre.getText().toString();
        }else{
            mensaje = "Error algo salio mal: "+a.error;
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
