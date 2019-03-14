package com.example.tpdm_u2_practica2_vallejo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConsultarPropietario extends AppCompatActivity {
    EditText consultarTxt;
    TextView telefono, nombre, domicilio, fecha;
    Button consultar, regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_propietario);

        consultarTxt = findViewById(R.id.consultarPropietario_buscarTelefono);

        telefono = findViewById(R.id.consultarPropietario_telefono);
        nombre = findViewById(R.id.consultarPropietario_nombre);
        domicilio = findViewById(R.id.consultarPropietario_domicilio);
        fecha = findViewById(R.id.consultarPropietario_fecha);
        regresar = findViewById(R.id.consultarPropietario_btnRegresar);


        consultar = findViewById(R.id.consultarPropietario_btnConsultar);


        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarPropietario();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void consultarPropietario() {
        Propietario propietario = new Propietario(this);
        Propietario vector [] = propietario.consultar("TELEFONO", consultarTxt.getText().toString());

        if(vector != null){
            for(int i = 0; i< vector.length; i++){
                Propietario temp = vector[i];
                telefono.setText(temp.getTelefono());
                nombre.setText(temp.getNombre());
                domicilio.setText(temp.getDomicilio());
                fecha.setText(temp.getFecha());
            }
            consultarTxt.setText("");
        }
    }


}
