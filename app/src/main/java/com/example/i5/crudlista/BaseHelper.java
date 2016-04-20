package com.example.i5.crudlista;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by i5 on 18/03/2016.
 */
public class BaseHelper extends SQLiteOpenHelper {
    String Tabla = "CREATE TABLE Tareas(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Nombre Text)";
    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
