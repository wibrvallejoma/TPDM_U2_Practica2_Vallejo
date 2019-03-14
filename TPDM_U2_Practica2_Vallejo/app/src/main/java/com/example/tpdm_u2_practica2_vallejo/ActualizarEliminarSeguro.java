package com.example.tpdm_u2_practica2_vallejo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActualizarEliminarSeguro extends AppCompatActivity {
    Button actualizar, eliminar, regresar;
    EditText descripcion, fecha;
    TextView titulo;
    Spinner tipo;
    int idSeguro;
    String telefonoPropietario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_eliminar_seguro);

        actualizar = findViewById(R.id.actualizarEliminarSeguro_btnActualizar);
        eliminar = findViewById(R.id.actualizarEliminarSeguro_btnEliminar);
        regresar = findViewById(R.id.actualizarEliminarSeguro_btnRegresar);
        descripcion = findViewById(R.id.actualizarEliminarSeguro_descripcion);
        fecha = findViewById(R.id.actualizarEliminarSeguro_fecha);
        titulo = findViewById(R.id.actualizarEliminarSeguro_titulo);

        tipo = findViewById(R.id.actualizarEliminarSeguro_tipo);

        Bundle parametros = getIntent().getExtras();
        telefonoPropietario = parametros.getString("TELEFONO_PROPIETARIO");
        titulo.setText("Editando seguro de "+parametros.getString("TELEFONO_PROPIETARIO"));
        descripcion.setText(parametros.getString("DESCRIPCION"));
        fecha.setText(parametros.getString("FECHA"));
        tipo.setSelection(parametros.getInt("TIPO"));
        idSeguro = parametros.getInt("IDSEGURO");

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarSeguro();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSeguro();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void eliminarSeguro() {
        Seguro a = new Seguro(this);
        String mensaje;
        boolean respuesta = a.elimiar(new Seguro(idSeguro, descripcion.getText().toString(),
                fecha.getText().toString(), Integer.parseInt(tipo.getSelectedItem().toString()), telefonoPropietario));
        if(respuesta){
            mensaje = "Se pudo eliminar correctamente el Seguro: "+descripcion.getText().toString();
            finish();
        }else{
            mensaje = "Error algo salio mal: " + a.error;
        }
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }

    private void actualizarSeguro() {
        Seguro a = new Seguro(this);
        String mensaje;
        boolean respuesta = a.actualizar(new Seguro(idSeguro, descripcion.getText().toString(),
                fecha.getText().toString(), Integer.parseInt(tipo.getSelectedItem().toString()), telefonoPropietario));
        if(respuesta){
            titulo.setText("Editando "+descripcion.getText().toString());
            mensaje = "Se actualizo correctamente el Seguro: "+descripcion.getText().toString();
        }else{
            mensaje = "Error algo salio mal: "+a.error;
        }
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
