package com.example.tpdm_u2_practica2_vallejo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class InsertarSeguro extends AppCompatActivity {
    String telefonoPropietario;
    TextView titulo;
    Spinner tipo;
    EditText descripcion, fecha;
    Button insertar, regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_seguro);

        titulo = findViewById(R.id.insertarSeguro_titulo);
        tipo = findViewById(R.id.insertarSeguro_tipoCombo);

        descripcion = findViewById(R.id.insertarSeguro_descripcion);
        fecha = findViewById(R.id.insertarSeguro_fecha);

        insertar = findViewById(R.id.insertarSeguro_btnInsertar);
        regresar = findViewById(R.id.insertarSeguro_regresar);

        Bundle parametros = getIntent().getExtras();
        telefonoPropietario = parametros.getString("TELEFONO_PROPIETARIO");

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarSeguroNuevo();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titulo.setText(telefonoPropietario);
    }

    private void insertarSeguroNuevo() {
        String mensaje = "";
        Seguro seguro = new Seguro(this);
        boolean respuesta = seguro.insertar(new Seguro(-1, descripcion.getText().toString(),
                fecha.getText().toString(),tipo.getSelectedItemPosition()+1, telefonoPropietario));

        if(respuesta){
            mensaje = "Se pudo insertar correctamente el Seguro: "+descripcion.getText().toString();
        }else{
            mensaje = "Error, no se pudo crear el Seguro, respuesta de SQLite: "+seguro.error;
        }
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Atencion")
                .setMessage(mensaje)
                .setPositiveButton("ok", null)
                .show();
        descripcion.setText("");

        fecha.setText("");
        tipo.setSelection(0);
    }
}
