package com.vasquez.fernandez.jordan.logica;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vasquez.fernandez.jordan.datos.Conexion;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Categoria extends Conexion {
    //Definir los atributos
    private Integer id;
    private String nombre;

    //Definir ArrayList
    public static ArrayList<Categoria> listaCategoria = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private void cargarDatosCategorias(){
        //Leer los registros de la tabla categoria
        SQLiteDatabase db = this.getReadableDatabase();
        //Definir la consulta SQL
        String sqlString = "SELECT * FROM CATEGORIA ORDER BY NOMBRE;";
        //Ejecutar consulta SQL
        Cursor cursor = db.rawQuery(sqlString,null);
        //Limpiar la lista donde se almacenara los registros de la tabla ciudad
        listaCategoria.clear();
        //Agregar los registros a la lista
        while(cursor.moveToNext()){
            Categoria categoria= new Categoria();
            categoria.setId(cursor.getInt(0));
            categoria.setNombre(cursor.getString(1));
            listaCategoria.add(categoria);
        }
    }

    public String[] obtenerNombresCategoria(){
        cargarDatosCategorias();
        String nombreCategorias[] = new String[listaCategoria.size()];
        for (int i = 0; i < listaCategoria.size() ; i++) {
            nombreCategorias[i] = listaCategoria.get(i).getNombre();
        }
        return nombreCategorias;
    }

    public int obtenerIdCiudad(String nombre){

        for (int i = 0; i < listaCategoria.size() ; i++) {
            if (nombre.equalsIgnoreCase(listaCategoria.get(i).getNombre())) {
                return listaCategoria.get(i).getId();
            };
        }
        return 0;
    }

}
