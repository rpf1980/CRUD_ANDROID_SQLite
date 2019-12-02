package com.example.sqliteprueba01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
{
    EditText edtID, edtNombre, edtApellidos;
    Button btnBuscar, btnInsertar, btnActualizar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociamos los id's
        edtID = findViewById(R.id.idEdtID);
        edtNombre = findViewById(R.id.idEdtNombre);
        edtApellidos = findViewById(R.id.idEdtApellidos);

        btnBuscar = findViewById(R.id.idBtnBuscar);
        btnInsertar = findViewById(R.id.idBtnInsertar);
        btnActualizar = findViewById(R.id.idBtnActualizar);
        btnBorrar = findViewById(R.id.idBtnBorrar);

        //Para acceder a tu base de datos, crea una instancia de tu subclase de SQLiteOpenHelper
        final BBDD_Helper helper = new BBDD_Helper(this);

        //Btn INSERTAR
        btnInsertar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Obtiene el repositorio de datos en modo de escritura
                SQLiteDatabase db = helper.getWritableDatabase();

                // Crea un nuevo mapa de valores para los campos de la base de datos ( inserta un registro de datos )
                ContentValues values = new ContentValues();
                values.put(Estructura_BD.NOMBRE_COLUMNA1, edtID.getText().toString());
                values.put(Estructura_BD.NOMBRE_COLUMNA2, edtNombre.getText().toString());
                values.put(Estructura_BD.NOMBRE_COLUMNA3, edtApellidos.getText().toString());

                // Inserta una nueva fila, devolviéndonos el valor de clave primaria del nuevo registro
                long newRowId = db.insert(Estructura_BD.TABLE_NAME, null, values);

                Toast.makeText(getApplicationContext(), "Se guardó el registro con clave " + newRowId, Toast.LENGTH_LONG).show();

                //Reseteamos campos
                edtID.setText("");
                edtNombre.setText("");
                edtApellidos.setText("");
            }
        });

        //Btn BORRAR
        btnBorrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Obtiene el repositorio de datos en modo de escritura
                SQLiteDatabase db = helper.getWritableDatabase();

                // Nos pide el campo del criterio ( el ID )
                String selection = Estructura_BD.NOMBRE_COLUMNA1 + " LIKE ?";

                // Capturamos el valor escrito en esa columna1 ( el ID )
                String[] selectionArgs = { edtID.getText().toString() };
                // Instrucción SQL
                int deletedRows = db.delete(Estructura_BD.TABLE_NAME, selection, selectionArgs);

                Toast.makeText(getApplicationContext(), "Se borró el registro con clave: " + edtID.getText().toString(), Toast.LENGTH_LONG).show();

                //Reseteamos campos
                edtID.setText("");
                edtNombre.setText("");
                edtApellidos.setText("");
            }
        });

        //Btn BUSCAR
        btnBuscar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Método que permite hacer nuestra base de datos de lectura
                SQLiteDatabase db = helper.getReadableDatabase();

                // Está definiendo una proyección que especifíca qué columna de nuestra bd
                // va a usar en nuestra consulta
                String[] projection = {
                        //Estructura_BD._ID, Omitimos que nos de el ID
                        Estructura_BD.NOMBRE_COLUMNA2, //Nos devuelve el nombre
                        Estructura_BD.NOMBRE_COLUMNA3  //Nos devuelve los apellidos
                };

                // Filtrado de registros
                String selection = Estructura_BD.NOMBRE_COLUMNA1 + " = ?"; //ID será el campo del criterio
                String[] selectionArgs = { edtID.getText().toString() }; //Nos pide de dónde viene el criterio o criterios

                // Si nuestras consultas devolvieran 10 , 20 registros ( un decir ) sí sería bueno ordenarlos
                // En nuestro caso lo dejamos COMENTADO
                /*String sortOrder =
                        Estructura_BD.COLUMN_NAME_SUBTITLE + " DESC";*/

                try
                {
                    // Creación del Objeto de tipo Cursor
                    Cursor cursor = db.query(
                            Estructura_BD.TABLE_NAME,   // Tabla de la consulta
                            projection,             // Este parámetro corresponde al array de las columnas que debe devolver
                            selection,              // Este parámetro corresponde a las columnas que tienen el criterio
                            selectionArgs,          // Hace referencia de dónde viene el criterio
                            null,                   // hace referencia a agrupar las consultas por denominación
                            null,                   // filtrar filas por grupos
                            null
                    );

                    //Movemos el cursor al primer registro de nuestro resulset
                    cursor.moveToFirst();

                    //Ahora leemos la información requerida
                    edtNombre.setText(cursor.getString(0));
                    edtApellidos.setText(cursor.getString(1));
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "No se encontró registro", Toast.LENGTH_LONG).show();
                }



            }
        });

        //Btn ACTUALIZAR
        btnActualizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SQLiteDatabase db = helper.getWritableDatabase();

                // Nuevos valores para los registros
                String title = "MyNewTitle";
                ContentValues values = new ContentValues();
                values.put(Estructura_BD.NOMBRE_COLUMNA2, edtNombre.getText().toString());
                values.put(Estructura_BD.NOMBRE_COLUMNA3, edtApellidos.getText().toString());

                // Esto sería la columna que vamos a usar como criterio
                String selection = Estructura_BD.NOMBRE_COLUMNA1 + " LIKE ?";
                String[] selectionArgs = { edtID.getText().toString() };

                //Método que se encargará de realizar la actualización ( update() )
                int count = db.update(
                        Estructura_BD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(), "Se actualizó el registro", Toast.LENGTH_LONG).show();

                //Reseteamos campos
                edtID.setText("");
                edtNombre.setText("");
                edtApellidos.setText("");
            }
        });
    }
}
