package com.vasquez.fernandez.jordan.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexion extends SQLiteOpenHelper {

    public static Context contextApp;
    public static String nombreBD = "LugaresTuristicosDB";
    public static int version = 1;
    public Conexion() {
        super(contextApp, nombreBD, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Instrucciones DDL y DML, solo se ejecuta una vez al instalar la app y abrirlo por primera vez
        //DDL Categoria y ciudad
        db.execSQL(Tablas.tablaCategoria);
        db.execSQL(Tablas.tablaLugarTuristico);

        //DML Categoria y ciudad
        for (int i = 0; i < Tablas.tablaCategoriaDatos.length; i++) {
            db.execSQL(Tablas.tablaCategoriaDatos[i]);
        }

        for (int i = 0; i < Tablas.tablaLugarTuristicoDatos.length; i++) {
            db.execSQL(Tablas.tablaLugarTuristicoDatos[i]);
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
