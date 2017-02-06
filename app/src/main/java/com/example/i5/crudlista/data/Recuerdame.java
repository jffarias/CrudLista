package com.example.i5.crudlista.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import com.example.i5.crudlista.data.RecuerdameContract.RecuerdameEntry;
/**
 * Created by Jesús Flor Farias on 15/10/2016.
 */

public class Recuerdame {
    private String mId;
    private String mTitle;
    private String mDescription;
    private int mImage;

    public Recuerdame(String title, String description, int image){
        this.mId = UUID.randomUUID().toString();
        this.mTitle = title;
        this.mDescription = description;
        this.mImage = image;
    }

    public Recuerdame(Cursor cursor){
        mId = cursor.getString(cursor.getColumnIndex(RecuerdameEntry.ID));
        mTitle = cursor.getString(cursor.getColumnIndex(RecuerdameEntry.TITLE));
        mDescription = cursor.getString(cursor.getColumnIndex(RecuerdameEntry.DESCRIPTION));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RecuerdameEntry.ID, mId);
        values.put(RecuerdameEntry.TITLE, mTitle);
        values.put(RecuerdameEntry.DESCRIPTION, mDescription);
        return values;
    }
    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public int getmImage(){
        return mImage;
    }

    @Override
    public String toString() {
        return "Recuerdame{" +
                "ID='" + mId + '\'' +
                ", Titulo='" + mTitle + '\'' +
                ", Descripcion='" + mDescription + '\'' +
                '}';
    }
}
