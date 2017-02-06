package com.example.i5.crudlista.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.i5.crudlista.R;

import static com.example.i5.crudlista.data.RecuerdameContract.RecuerdameEntry;

/**
 * Created by i5 on 15/10/2016.
 */

public class RecuerdameDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lawyers.db";

    public RecuerdameDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RecuerdameEntry.TABLE_NAME + " ("
                + RecuerdameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RecuerdameEntry.ID + " TEXT NOT NULL,"
                + RecuerdameEntry.TITLE + " TEXT NOT NULL,"
                + RecuerdameEntry.DESCRIPTION + " TEXT NOT NULL,"
                + "UNIQUE (" + RecuerdameEntry.ID + "))");

        // Insertar datos ficticios para prueba inicial
        mockData(db);
    }



    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Recuerdame("Terminar tesis", "Avanzar en el cap 4",
                R.drawable.ic_hourglass_sand));
        mockLawyer(sqLiteDatabase, new Recuerdame("Android", "Programar en Android",
                R.drawable.ic_hourglass_sand));
        mockLawyer(sqLiteDatabase, new Recuerdame("Dormir", "Dormir cuando se pueda",
                R.drawable.ic_hourglass_sand));
    }

    public long mockLawyer(SQLiteDatabase db, Recuerdame recuerdame) {
        return db.insert(
                RecuerdameEntry.TABLE_NAME,
                null,
                recuerdame.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long saveRecuerdame(Recuerdame recuerdame) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                RecuerdameEntry.TABLE_NAME,
                null,
                recuerdame.toContentValues());

    }

    public Cursor getAllRecuerdame() {
        return getReadableDatabase()
                .query(
                        RecuerdameEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getRecuerdameById(String recuerdameId) {
        Cursor c = getReadableDatabase().query(
                RecuerdameEntry.TABLE_NAME,
                null,
                RecuerdameEntry.ID + " LIKE ?",
                new String[]{recuerdameId},
                null,
                null,
                null);
        return c;
    }



    //eliminar un recuerdo
    public int deleteRecuerdo(String recuerdoId){
        return getWritableDatabase().delete(
                RecuerdameEntry.TABLE_NAME,
                RecuerdameEntry.ID + " LIKE ?",
                new String[]{recuerdoId});
    }

    public int updateRecuerdame(Recuerdame recuerdame, String recuerdameId) {
        return getWritableDatabase().update(
                RecuerdameEntry.TABLE_NAME,
                recuerdame.toContentValues(),
                RecuerdameEntry.ID + " LIKE ?",
                new String[]{recuerdameId}
        );
    }
}
