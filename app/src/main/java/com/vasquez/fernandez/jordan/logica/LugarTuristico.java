package com.vasquez.fernandez.jordan.logica;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vasquez.fernandez.jordan.datos.Conexion;

import java.util.ArrayList;

public class LugarTuristico extends Conexion {

    private int id;
    private String nombre;
    private String dias;
    private String horarioAtencion;
    private double costoIngtreso;
    private String foto;
    private int categoriaId;
    private String categoria;


    //Definir un ArrayList para almacenar los datos de clientes
    public static ArrayList<LugarTuristico> listadoLugaresTuristicos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public double getCostoIngtreso() {
        return costoIngtreso;
    }

    public void setCostoIngtreso(double costoIngtreso) {
        this.costoIngtreso = costoIngtreso;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void cargarDatos() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlString = "SELECT L.*,C.nombre as categoria FROM lugar_turistico L inner join categoria c " +
                "ON (L.categoria_id == C.id) order by L.nombre;";

        Cursor cursor = db.rawQuery(sqlString,null);

        listadoLugaresTuristicos.clear();

        while (cursor.moveToNext()){
            LugarTuristico lugarTuristico = new LugarTuristico();
            lugarTuristico.setId(cursor.getInt(0));
            lugarTuristico.setNombre(cursor.getString(1));
            lugarTuristico.setDias(cursor.getString(2));
            lugarTuristico.setHorarioAtencion(cursor.getString(3) + "-" + cursor.getString(4));
            lugarTuristico.setCostoIngtreso(cursor.getDouble(5));
            lugarTuristico.setFoto(cursor.getString(6));
            lugarTuristico.setCategoriaId(cursor.getInt(7));
            lugarTuristico.setCategoria(cursor.getString(8));

            listadoLugaresTuristicos.add(lugarTuristico);
        }

    }

    public long agregar(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",this.getNombre());
        registro.put("dias",this.getDias());
        registro.put("hora_inicio",this.getHorarioAtencion().split("-")[0]);
        registro.put("hora_fin",this.getHorarioAtencion().split("-")[1]);
        registro.put("costo_ingreso",this.getCostoIngtreso());
        registro.put("foto",this.getFoto());
        registro.put("categoria_id",this.getCategoriaId());
        long resultado = db.insert("lugar_turistico",null,registro);
        return resultado;
    }


}
