package com.example.tpdm_u2_practica2_vallejo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Propietario {
    private BaseDatos base;
    private String telefono; //ID del propietario
    private String nombre, domicilio, fecha;
    protected String error;

    public Propietario(Activity activity){
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public Propietario(String telefono, String nombre, String domicilio, String fecha){
        this.telefono = telefono;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.fecha = fecha;
    }

    //CRUD

    public boolean insertar(Propietario propietario){
        try{
            SQLiteDatabase transaccionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("TELEFONO", propietario.getTelefono());
            datos.put("NOMBRE", propietario.getNombre());
            datos.put("DOMICILIO", propietario.getDomicilio());
            datos.put("FECHA", propietario.getFecha());

            long resultado = transaccionInsertar.insert("PROPIETARIO", null, datos);

            transaccionInsertar.close();

            if(resultado == -1 ) return false;

        }catch(SQLiteException e){
            error = e.getMessage();
            return  false;
        }
        return true;
    }

    //Consultar 1
    public Propietario[] consultar(String columna, String condicion){
        Propietario[] resultado = null;
        try{
            SQLiteDatabase transaccionConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROPIETARIO WHERE TELEFONO = '"+condicion+"'";

            if(columna.startsWith("NOMBRE")){
                SQL = "SELECT * FROM PROPIETARIO WHERE NOMBRE LIKE '%"+condicion+"%'";
            }
            Cursor c = transaccionConsulta.rawQuery(SQL, null);
            if(c.moveToFirst()){
                resultado = new Propietario[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos] = new Propietario(c.getString(0), c.getString(1),
                            c.getString(2), c.getString(3));
                    pos ++;
                }while(c.moveToNext());
            }
            transaccionConsulta.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return null;
        }
        return resultado;
    }

    //Consultar TODOS
    public Propietario[] consultar(){
        Propietario[] resultado = null;
        try{
            SQLiteDatabase transaccionConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROPIETARIO";

            Cursor c = transaccionConsulta.rawQuery(SQL, null);
            if(c.moveToFirst()){
                resultado = new Propietario[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos] = new Propietario(c.getString(0), c.getString(1),
                            c.getString(2), c.getString(3));
                    pos ++;
                }while(c.moveToNext());
            }
            transaccionConsulta.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return null;
        }
        return resultado;
    }

    public boolean eliminar(Propietario propietario){
        int resultado;
        try{
            SQLiteDatabase transaccionEliminar = base.getWritableDatabase();
            String claves[] = {""+propietario.getTelefono()};
            resultado = transaccionEliminar.delete("PROPIETARIO", "TELEFONO = ?", claves);
            transaccionEliminar.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(Propietario propietario){
        try{
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("NOMBRE", propietario.getNombre());
            datos.put("DOMICILIO", propietario.getDomicilio());
            datos.put("FECHA", propietario.getFecha());

            long resultado =
                    transaccionActualizar.update("PROPIETARIO", datos,
                            "TELEFONO = ?", new String[]{"" + propietario.getTelefono()});
            transaccionActualizar.close();
            if(resultado == -1) return false;
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
