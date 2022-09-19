package com.vasquez.fernandez.jordan.datos;

public class Tablas {

    public static String tablaCategoria =  "CREATE TABLE categoria( id int PRIMARY KEY, " +
            "nombre varchar(50) not NULL );";

    public static String tablaCategoriaDatos[] = new String[] {
            "insert INTO categoria(id,nombre) values (1,'Reserva'); " ,
                    "insert INTO categoria(id,nombre) values (2,'Museo'); " ,
                    "insert INTO categoria(id,nombre) values (3,'Recreo'); " ,
                    "insert INTO categoria(id,nombre) values (4,'Campestre');"
    };

    public static String tablaLugarTuristico = "CREATE TABLE lugar_turistico (\n" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            " nombre varchar(100) not NULL,\n" +
            " dias varchar(20) NOT NULL,\n" +
            " hora_inicio char(5) NOT NULL,\n" +
            " hora_fin char(5) NOT NULL,\n" +
            " costo_ingreso real NOT NULL,\n" +
            " foto blob,\n" +
            " categoria_id int not NULL,\n" +
            " FOREIGN KEY (categoria_id) REFERENCES categoria(id)\n" +
            ");";
    public static String tablaLugarTuristicoDatos[] = new String[] {
            "INSERT INTO lugar_turistico VALUES (1,'Museo Sipan','Lu-Ma-Mi','08:00','12:00',20.5,null,1); ",
            "INSERT INTO lugar_turistico VALUES (2,'Las Pircas','Ma-Mi-Ju-Vi','08:00','18:00',25,null,3);"
    };

}
