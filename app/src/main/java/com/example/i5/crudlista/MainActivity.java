package com.example.i5.crudlista;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(MainActivity.this, Segunda.class));
            }
        });
        cargar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_listar) {
            //return true;
            //startActivity(new Intent(this, Segunda.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public void cargar(){
        BaseHelper dbhelper = new BaseHelper(this, "DBAPP", null, 1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        if(db != null){
            Cursor c = db.rawQuery("select * from Tareas", null);
            int cantidad = c.getCount();
            int j=0;
            String[] arreglo = new String[cantidad];
            if(c.moveToFirst()){
                do{
                    String linea = c.getInt(0) + " " + c.getString(1);
                    arreglo[j] = linea;
                    j++;
                }while(c.moveToNext());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arreglo);
            ListView lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(adapter);
        }
    }
}
