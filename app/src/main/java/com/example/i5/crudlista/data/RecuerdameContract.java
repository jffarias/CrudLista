package com.example.i5.crudlista.data;

import android.provider.BaseColumns;

/**
 * Created by Jesús Flor Farias on 15/10/2016.
 */

public class RecuerdameContract {
    public static abstract class RecuerdameEntry implements BaseColumns {
        public static final String TABLE_NAME ="recuerdame";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
    }
}
