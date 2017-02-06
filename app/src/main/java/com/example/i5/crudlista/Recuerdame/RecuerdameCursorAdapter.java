package com.example.i5.crudlista.Recuerdame;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.i5.crudlista.R;
import com.squareup.picasso.Picasso;

import com.example.i5.crudlista.data.RecuerdameContract.RecuerdameEntry;
/**
 * Created by i5 on 15/10/2016.
 */

public class RecuerdameCursorAdapter extends CursorAdapter {
    public RecuerdameCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_recuerdame, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Referencias UI.
        //ImageView avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView descripcion = (TextView) view.findViewById(R.id.tv_description);

        // Get valores.
        String tituloDB = cursor.getString(cursor.getColumnIndex(RecuerdameEntry.TITLE));
        String descriptionDB = cursor.getString(cursor.getColumnIndex(RecuerdameEntry.DESCRIPTION));

        // Setup.
        title.setText(tituloDB);
        descripcion.setText(descriptionDB);
    }
}
