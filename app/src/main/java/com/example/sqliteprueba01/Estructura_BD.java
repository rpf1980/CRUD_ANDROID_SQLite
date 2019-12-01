package com.example.sqliteprueba01;

import android.provider.BaseColumns;

public class Estructura_BD
{
    //Atributos
    public static final String TABLE_NAME = "datosPersonales";
    public static final String NOMBRE_COLUMNA1 = "Id";
    public static final String NOMBRE_COLUMNA2 = "Nombre";
    public static final String NOMBRE_COLUMNA3 = "Apellidos";

    //Constructor
    private Estructura_BD(){}

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BD.TABLE_NAME + " (" +
                    Estructura_BD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY," +
                    Estructura_BD.NOMBRE_COLUMNA2 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BD.NOMBRE_COLUMNA3 + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BD.TABLE_NAME;
}