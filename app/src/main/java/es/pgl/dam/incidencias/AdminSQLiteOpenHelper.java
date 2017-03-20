package es.pgl.dam.incidencias;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Enrique on 09/02/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //creamos el constructor para la clase.
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    //creamos las tablas con sus atributos.
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tecnicos(_idtec Integer primary key,nombre text)");
        db.execSQL("create table handling(_id Integer primary key,nombre text)");
        db.execSQL("create table compania(_codigo Integer primary key,nombre text)");
        db.execSQL("create table incidencia(_idinc Integer primary key,fechainicio text,horainicio text,fechafin text," +
                "horafin text, lugar text, nummost int,descripcion text, solucion text,compa text, handling text, " +
                "tecnico text, numtarjeta text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
