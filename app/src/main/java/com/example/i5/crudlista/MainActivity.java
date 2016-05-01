package com.example.i5.crudlista;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    String[] arreglo;
    ListView lvTareas;
    protected Object noActionMode;
    public int selectedItem=-1;
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
        Notification();

        lvTareas = (ListView) findViewById(R.id.lista);
        lvTareas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvTareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (noActionMode != null) {
                    return false;
                }
                selectedItem = position;
                //menuTarea
                noActionMode = MainActivity.this.startActionMode(menuMain);
                view.setSelected(true);
                view.setBackgroundColor(Color.CYAN);
                return true;
            }
        });
    }

    /*@Override
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
    }*/


    public void cargar(){
        BaseHelper dbhelper = new BaseHelper(this, "DBAPP", null, 1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        if(db != null){
            Cursor c = db.rawQuery("select * from Tareas", null);
            int cantidad = c.getCount();
            int j=0;
            arreglo = new String[cantidad];
            if(c.moveToFirst()){
                do{
                    String linea = c.getInt(0) + " " + c.getString(1);
                    arreglo[j] = linea;
                    j++;
                }while(c.moveToNext());
            }
            c.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arreglo);
            ListView lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(adapter);
        }
    }

    private ActionMode.Callback menuMain = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
                case R.id.menu_borrar:
                    Borrar();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            cargar();
            noActionMode = null;
            selectedItem = -1;
        }
    };

    private void Borrar(){
        BaseHelper dbhelper = new BaseHelper(this, "DBAPP", null, 1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        int id= Integer.parseInt(arreglo[selectedItem].split(" ")[0]);
        if(db != null){
            long res = db.delete("Tareas", "Id="+id, null);
            if(res > 0){
                Toast.makeText(getApplicationContext(), "Tarea Eliminada "+id, Toast.LENGTH_SHORT).show();
                cargar();
            }
        }
    }

    public void Notification(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                        .setContentTitle("Recuerdame App")
                        .setContentText("Click para configurar")
                        .setTicker("RecuardameApp")
                        .setContentInfo("2");
        Intent inotification = new Intent(this, MainActivity.class);
        PendingIntent intentPendiente = PendingIntent.getActivity(MainActivity.this, 0, inotification,0 );

        notification.setContentIntent(intentPendiente);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(10,notification.build());
    }
}
