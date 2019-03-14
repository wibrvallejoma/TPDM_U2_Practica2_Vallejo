package com.example.tpdm_u2_practica2_vallejo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Seguro {
    private BaseDatos base;
    private int idSeguro, tipo;
    private String descripcion, fecha, telefono_propietario;
    protected String error;

    public Seguro(Activity activity){
        base = new BaseDatos(activity, "aseguradora", null, 1);
    }

    public Seguro(int idSeguro, String descripcion, String fecha, int tipo, String telefono_propietario){
        this.idSeguro = idSeguro;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.telefono_propietario = telefono_propietario;
    }

    //CRUD
    public boolean insertar(Seguro seguro){
        try{
            SQLiteDatabase transaccionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();

            datos.put("DESCRIPCION", seguro.getDescripcion());
            datos.put("FECHA", seguro.getFecha());
            datos.put("TIPO", seguro.getTipo());
            datos.put("TELEFONO_PROPIETARIO", seguro.getTelefono_propietario());

            long resultado = transaccionInsertar.insert("SEGURO", "IDSEGURO", datos);

            transaccionInsertar.close();

            if(resultado == -1 ) return false;

        }catch(SQLiteException e){
            error = e.getMessage();
            return  false;
        }
        return true;
    }
    public int contarSeguros(String columna, String condicion){
        int resultado = 0;
        try{
            SQLiteDatabase transaccionContar = base.getReadableDatabase();
            String SQL = "SELECT COUNT(*) FROM SEGURO WHERE TELEFONO_PROPIETARIO = '"+condicion+"'";

            Cursor c = transaccionContar.rawQuery(SQL, null);
            if(c.moveToFirst()){
                resultado = c.getInt(0);
            }
            transaccionContar.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return 0;
        }
        return resultado;
    }

    public Seguro[] consultar(String columna, String condicion){
        Seguro[] resultado = null;
        try{
            SQLiteDatabase transaccionConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM SEGURO WHERE IDSEGURO = "+condicion;

            if(columna.startsWith("TELEFONO_PROPIETARIO")){
                SQL = "SELECT * FROM SEGURO WHERE TELEFONO_PROPIETARIO = '"+condicion+"'";
            }
            Cursor c = transaccionConsulta.rawQuery(SQL, null);
            if(c.moveToFirst()){
                resultado = new Seguro[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos] = new Seguro(c.getInt(0), c.getString(1),
                            c.getString(2), c.getInt(3), c.getString(4));
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

    public Seguro[] consultar(){
        Seguro[] resultado = null;
        try{
            SQLiteDatabase transaccionConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM SEGURO";

            Cursor c = transaccionConsulta.rawQuery(SQL, null);
            if(c.moveToFirst()){
                resultado = new Seguro[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos] = new Seguro(c.getInt(0), c.getString(1),
                            c.getString(2), c.getInt(3), c.getString(4));
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

    public boolean elimiar(Seguro seguro){
        int resultado;
        try{
            SQLiteDatabase transaccionEliminar = base.getWritableDatabase();
            String claves[] = {""+seguro.getIdSeguro()};
            resultado = transaccionEliminar.delete("SEGURO", "IDSEGURO = ?", claves);
            transaccionEliminar.close();
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(Seguro seguro){
        try{
            SQLiteDatabase transaccionActualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();

            datos.put("DESCRIPCION", seguro.getDescripcion());
            datos.put("FECHA", seguro.getFecha());
            datos.put("TIPO", seguro.getTipo());
            datos.put("TELEFONO_PROPIETARIO", seguro.getTelefono_propietario());

            long resultado =
                    transaccionActualizar.update("SEGURO", datos,
                            "IDSEGURO = ?", new String[]{"" + seguro.getIdSeguro()});
            transaccionActualizar.close();
            if(resultado == -1) return false;
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public int getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTelefono_propietario() {
        return telefono_propietario;
    }

    public void setTelefono_propietario(String telefono_propietario) {
        this.telefono_propietario = telefono_propietario;
    }
}
