package com.example.tpdm_u2_practica2_vallejo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetallesPropietario extends AppCompatActivity {
    TextView titulo, telefono, nombre, domicilio, fecha, seguros;
    String idPropietario;
    Button verSeguros, agregarSeguro, editar, regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_propietario);

        titulo = findViewById(R.id.detallesPropietario_titulo);
        telefono = findViewById(R.id.detallesPropietario_telefono);
        nombre = findViewById(R.id.detallesPropietario_nombre);
        domicilio = findViewById(R.id.detallesPropietario_domicilio);
        fecha = findViewById(R.id.detallesPropietario_fecha);


        agregarSeguro = findViewById(R.id.detallesPropietario_btnAgregarSeguro);
        verSeguros = findViewById(R.id.detallesPropietario_btnVerSeguros);
        editar = findViewById(R.id.detallesPropietario_btnEditar);
        regresar = findViewById(R.id.detallesPropietario_btnRegresar);


        Bundle parametros = getIntent().getExtras();
        titulo.setText("Detalles de "+parametros.getString("TELEFONO"));
        telefono.setText(parametros.getString("TELEFONO"));
        nombre.setText(parametros.getString("NOMBRE"));
        domicilio.setText(parametros.getString("DOMICILIO"));
        fecha.setText(""+parametros.getString("FECHA"));
        idPropietario = parametros.getString("TELEFONO");


        agregarSeguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invocarInsertarSeguro();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarAlerta();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verSeguros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invocarConsultarSeguros();
            }
        });

    }

    private void invocarConsultarSeguros() {
        Intent consultarSeguros = new Intent(this, ConsultarSeguros.class );

        consultarSeguros.putExtra("TELEFONO_PROPIETARIO", idPropietario);

        startActivity(consultarSeguros);
    }

    private void invocarInsertarSeguro() {
        Intent insertarSeguro = new Intent(this, InsertarSeguro.class );

        insertarSeguro.putExtra("TELEFONO_PROPIETARIO", idPropietario);

        startActivity(insertarSeguro);
    }

    private void mostrarAlerta() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle("Atencion");
        alerta.setMessage("Deseas modificar/editar el Propietario: " + nombre.getText().toString() + "?");
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                invocarEliminarActualizar();
            }
        });
        alerta.setNegativeButton("No", null);
        alerta.show();
    }

    private void invocarEliminarActualizar() {
        Intent eliminaActualiza = new Intent(this, ActualizarEliminarPropietario.class );

        eliminaActualiza.putExtra("TELEFONO", idPropietario);
        eliminaActualiza.putExtra("NOMBRE", nombre.getText().toString());
        eliminaActualiza.putExtra("DOMICILIO", domicilio.getText().toString());
        eliminaActualiza.putExtra("FECHA", fecha.getText().toString());

        startActivity(eliminaActualiza);
    }

}
